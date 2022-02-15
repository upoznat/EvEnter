package com.eventer.paymentservice.job;


import com.eventer.paymentservice.dto.ExternalPaymentRequestDTO;
import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import com.eventer.paymentservice.parser.PartialStatementParser;
import com.eventer.paymentservice.service.PaymentTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty("payment-service.parsing.enabled")
public class PaymentsFileParsingJob {

    /**
     * Postfix "\archive" za pravljenje / trazenje arhivnih podfoldera.
     */
    private static final String DIR_ARC = "\\archive";

    /**
     * Postfix "\error" za pravljenje / trazenje podfoldera za izvode s greskama.
     */
    private static final String DIR_ERR = "\\error";
    /**
     * Postfix "_error" kojim oznacavamo fajlove koji su preskoceni zbog greske.
     */
    private static final String FILE_ERROR_POSTFIX = "_error";
    /**
     * Ekstenzija za tekst fajlove.
     */
    private static final String FILE_EXTENSION = ".txt";
    /**
     * String za kreiranje testnog fajla.
     */
    private static final String FILE_TEST = "\\__TEMP.test";

    @Value("${parsing.directory}")
    private static String baseDirLocation;

    @Autowired
    PartialStatementParser parser;

    @Autowired
    PaymentTransactionService paymentService;

    @Scheduled(initialDelay = 15000, fixedDelay = 60000)
    public void executeJob() {

        File baseDir = new File(baseDirLocation);

        if (!baseDir.exists() || !baseDir.isDirectory() || !baseDir.canRead() || !isOperable(baseDir)) {
            log.error(" - Osnovna direktorija nije podesena kako treba (\"" + baseDir.getAbsolutePath());
            return;
        }

        List<File> statementFiles = null;

        try {
            statementFiles = getAllNewStatementsFromSubfolders(baseDir);

            List<ExternalPaymentRequestDTO> dtos = getDtosFromFiles(statementFiles);

            saveStatementsFromDTOs(dtos);

        } catch (Exception e) {
            log.error(" - Greska! Obustavljen uvoz ");

        } finally {
            if (statementFiles != null && !statementFiles.isEmpty()) {
                log.info(".execute - Zapoceto arhiviranje fajlova.");
                doHandleFiles(statementFiles);
            } else {
                log.info(".execute - Nije bilo fajlova, zavrsen posao.");
            }


        }
    }

    /**
     * Snima izvode u tabelu
     *
     * @param dtos - PaymentFromFileDTO lista koju snimamo i arhiviramo fajlove.
     * @throws PaymentTransactionProcessingException - U slucaju greske pri snimanju.
     */
    private void saveStatementsFromDTOs(List<ExternalPaymentRequestDTO> dtos)
            throws PaymentTransactionProcessingException {

        dtos.forEach(dto -> paymentService.createAndSavePayment(dto));


    }

    /**
     * Metoda za arhiviranje / obelezavanje fajlova izvoda zavisno od podesavanja u properties fajlu.
     *
     * @param dtos - DTO-i
     */
    private void doHandleFiles(List<File> dtos) {

        File currentFile = null;
        boolean hasErrors = false;

        for (File dto : dtos) {
            log.info(".doHandlePartialsFiles - Arhivira se: " + dto.getName() + ";");
            if (currentFile == null) {
                currentFile = dto;
            }

            if (!currentFile.equals(dto)) {

                doArchiveAndRename(currentFile, hasErrors);

                hasErrors = false;
                currentFile = dto;
            }

//            if (!hasErrors && (!dto.getImportStatus().equals(.NOMINAL)
//                    && !dto.getImportStatus().equals(.DUPLICATE_STATEMENT))) {
//                hasErrors = true;
//            }

        }
        // poslednji fajl mora posebno
        doArchiveAndRename(currentFile, hasErrors);
    }

    private List<ExternalPaymentRequestDTO> getDtosFromFiles(List<File> files) {

        List<ExternalPaymentRequestDTO> dtos = new ArrayList<ExternalPaymentRequestDTO>();
        List<File> potentialErrorFiles = new ArrayList<File>();

        for (File file : files) {
            try {
                List<ExternalPaymentRequestDTO> dtoListTemp = null;

                dtoListTemp = parser.parseFile(file);

                dtos.addAll(dtoListTemp);
            } catch(Exception e){
                potentialErrorFiles.add(file);
            }

        }
        if (!potentialErrorFiles.isEmpty()) {

            for (File file : potentialErrorFiles) {
                doArchiveAndRename(file, true);
            }
        }
        return dtos;
    }


    /**
     * S obzirom da se ispostavilo da nijedna od Javinih metoda ne funkcionise kako bi trebala (canWrite(),
     * setWritable(), FilePermission), ova metoda testira prava pristupa tako sto pokusa da napravi i obrise temp fajl.
     *
     * @param dir - Direktorija za koju se proveravaju prava.
     * @return - True ako su odgovarajuca prava.
     */
    private boolean isOperable(File dir) {
        try {
            File temp = new File(dir.getAbsolutePath() + FILE_TEST);
            if (!temp.exists()) {
                temp.createNewFile();
            }
            return temp.delete();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Dohvata fajlove svih novih izvoda.
     *
     * @param dir - Osnovna direktorija izvoda koja sadrzi podfoldere sa izvodima.
     * @return ArrayList - Header fajlovi.
     * @throws PaymentTransactionProcessingException - U slucaju neodgovarajucih prava pristupa.
     */
    private List<File> getAllNewStatementsFromSubfolders(File dir) throws PaymentTransactionProcessingException {
        File[] fileArr = null;
        List<File> statements = new ArrayList<File>();

        fileArr = dir.listFiles();

        for (File currentDir : fileArr) {
            if (!currentDir.isDirectory()) {
                return null;
            } else {

                boolean subfoldersMade = false;
                File[] files = currentDir.listFiles();


                for (File file : files) {
                    if (isTxtFile(file)) {
                        if (!subfoldersMade) {
                            if (!currentDir.canRead() || !isOperable(currentDir)) {
                                log.error("Neodgovarajuca prava "
                                        + "pristupa za direktoriju ["
                                        + currentDir.getAbsolutePath() + "]. Preskace se!\n"
                                        + "Prava pristupa: [R:" + (currentDir.canRead() ? 1 : 0)
                                        + "][W:" + (isOperable(currentDir) ? 1 : 0) + "];");
                                throw new PaymentTransactionProcessingException();
                            }
                            subfoldersMade = createArchiveIfNonexistant(currentDir)
                                    && createErrorStashIfNonexistant(currentDir);

                            if (!subfoldersMade) {
                                log.error(
                                        "Nemoguce kreirati potrebne "
                                                + "poddirektorije na lokaciji \"" + currentDir + "\"");
                                throw new PaymentTransactionProcessingException();
                            }
                        }
                        statements.add(file);
                    }
                }
            }
        }
        return statements;
    }


    /**
     * Provera da li postoji arhivni podfolder i kreacija istog ako ne postoji.
     *
     * @param dir - Folder za koji se proverava ima li arhivu.
     * @return boolean - True ako se uspesno izvrsi metoda.
     */
    private boolean createArchiveIfNonexistant(File dir) {
        File arch = new File(dir.getPath() + DIR_ARC);
        if (!arch.exists()) {
            arch.mkdir();
        }

        return true;
    }

    /**
     * Provera da li postoji podfolder za fajlove s greskom i kreacija istog ako ne postoji.
     *
     * @param dir - Folder unutar kog se proverava.
     * @return boolean - True ako se uspesno izvrsi metoda.
     */
    private boolean createErrorStashIfNonexistant(File dir) {
        File arch = new File(dir.getPath() + DIR_ERR);
        if (!arch.exists()) {
            arch.mkdir();
        }

        return true;
    }


    private boolean isTxtFile(File file) {
        if (file.isDirectory()) {
            return false;
        }

        String name = file.getName();
        String postfix = name.substring(name.length() - +FILE_EXTENSION.length());
        if (postfix.equals(FILE_EXTENSION)) {
            return true;
        }
        return false;
    }

    /**
     * Metoda za arhiviranje i obelezavanje fajlova postfiksima.
     *
     * @param file - Fajl izvoda (header ili stavke)
     * @param hasErrors - Ima li gresaka.
     */
    private void doArchiveAndRename(File file, boolean hasErrors) {

        if (file == null) {
            log.error(".doArchiveAndRename - Prosledjen null! File: " + file + ";");
            return;
        }

        // Izbegavanje indexOutOfBounds ako ovde dospe fajl koji nema ekstenziju ".txt" a nekim cudom je prosao sve
        // validacije do ovde.
        if (!file.getName().contains(FILE_EXTENSION)) {
            log.error(".doArchiveAndRename - Nestandardan fajl! Tagujem kao error fajl: \"" + file.getAbsolutePath()
                    + "\".");
            file.renameTo(new File(file.getAbsolutePath() + FILE_ERROR_POSTFIX));
            return;
        }

        String fileLocation = file.getParent();
        String fileName = file.getName().substring(0, file.getName().length() - FILE_EXTENSION.length());

        if (hasErrors) {
                fileLocation += DIR_ERR;
                fileName += FILE_ERROR_POSTFIX;
        } else {
            fileLocation += DIR_ARC;
        }

        boolean done = file.renameTo(new File(fileLocation + "\\" + fileName + FILE_EXTENSION));

        if (!done) {
            log.info(" - Fajl [" + file.getName() + "] nije uspesno premesten! Ponovo se pokusava sa postfixom...");
            done = file.renameTo(
                    new File(fileLocation + "\\" + fileName + "[" + new Date().getTime() + "]" + FILE_EXTENSION));
            if (!done) {
                log.error(" - Fajl [" + file.getName() + "] neuspesan drugi pokusaj premestanja!");
            }
        } else {
            log.info(".doArchiveAndRename - Fajl [" + file.getName() + "] Uspesno arhiviran!");
        }
    }


}
