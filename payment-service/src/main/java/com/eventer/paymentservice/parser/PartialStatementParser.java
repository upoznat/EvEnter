package com.eventer.paymentservice.parser;

import com.eventer.paymentservice.dto.ExternalPaymentRequestDTO;
import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PartialStatementParser {

    public List<ExternalPaymentRequestDTO> parseFile(File file) throws PaymentTransactionProcessingException {

        List<ExternalPaymentRequestDTO> dtoList = new ArrayList<ExternalPaymentRequestDTO>();

        List<String> rows = null;
        try {
            rows = Files.readLines(file, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new PaymentTransactionProcessingException();
        }

        for(String row : rows) {

            ExternalPaymentRequestDTO dto = new ExternalPaymentRequestDTO();

            boolean storno = false;
            String brojRacuna = ParserUtils.getCustomerAccountNumber(row);

            String oznakaKnjizenja = ParserUtils.getPaymentType(row);

            String payerName = ParserUtils.getNameOfCustomer(row);

            String recipient = ParserUtils.getNameOfRecipient(row);

            String accNumFromFile = ParserUtils.getBankAccountInfo(row);

            String iznosSaParama = ParserUtils.getAmountInfo(row);
            Double amount = ParserUtils.doubleConverter(iznosSaParama);

            String stornoString = ParserUtils.getStorno(row);
            if (stornoString.equalsIgnoreCase("S")) {
                storno = true;
            }


            dto.setAmount(amount);

            String refNumModel = ParserUtils.getDebitAuthorizationNumberModel(row);

            String pozivNaBrojOdobrenja = ParserUtils.getCreditAuthorizationNumber(row);

            String svrhaPlacanja = ParserUtils.getDescriptionOfPayment(row);


            ParserUtils.getDateOfPayment(row);
           ParserUtils.getPaymentDescriptionCode(row);

            String oblikPlacanja = ParserUtils.getPaymentDescription(row);
            String sifraPlacanja = ParserUtils.getPaymentDescriptionCode(row);
            String paymentTypeFromFile = oblikPlacanja + sifraPlacanja;

            ParserUtils.getReclamationNumber(row);

            dtoList.add(dto);
        }

        return dtoList;
    }
}
