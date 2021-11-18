package com.eventer.paymentservice.parser;

import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ParserUtils {

    public static String getBankAccountInfo(String line) {
        return line.substring(0, 18).trim();
    }

    public static String getPaymentType(String line) {
        return line.substring(18, 20).trim();
    }

    public static String getStorno(String line) {
        return line.substring(28, 30).trim();
    }

    public static String getNameOfCustomer(String line) {
        return line.substring(30, 65).trim();
    }

    public static Date getDateOfPayment(String line) throws PaymentTransactionProcessingException {
        String dateOfPaymentString = line.substring(66, 72).trim();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
        Date dateOfPayment;
        try {
            dateOfPayment = sdf.parse(dateOfPaymentString);
        } catch (ParseException e) {
            throw new PaymentTransactionProcessingException();
        }
        return dateOfPayment;
    }

    public static String getCustomerAccountNumber(String line) {
        return line.substring(72, 90).trim();
    }

    public static String getAmountInfo(String line) {
        return line.substring(90, 105).trim();
    }

    public static String getPaymentDescription(String line) {
        return line.substring(106, 107).trim();
    }

    public static String getPaymentDescriptionCode(String line) {
        return line.substring(107, 109).trim();
    }

    public static String getDebitAuthorizationNumberModel(String line) {
        return line.substring(111, 113).trim();
    }

    public static String getDebitAuthorizationNumber(String line) {
        return line.substring(113, 135).trim();
    }

    public static String getCreditAuthorizationNumberModel(String line) {
        return line.substring(135, 137).trim();
    }

    public static String getCreditAuthorizationNumber(String line) {
        return line.substring(137, 159).trim();
    }

    public static String getDescriptionOfPayment(String line) {
        return line.substring(159, 195).trim();
    }

    public static String getCityOfRecipient(String line) {
        return line.substring(195, 205).trim();
    }

    public static String getNameOfRecipient(String line) {
        return line.substring(205, 240).trim();
    }

    public static String getReclamationNumber(String line) {
        return line.substring(240, 262).trim();
    }

    public static String getTransactionIdentifier(String line) {
        return line.substring(263, 281).trim();
    }

    public static Double doubleConverter(String string) {

        if (isStringLenghtValid(string)) {
            throw new IllegalArgumentException(string);
        }
        String stringNumber = string;
        stringNumber = StringUtils.stripStart(stringNumber, "0");
        stringNumber = StringUtils.leftPad(stringNumber, 3, "0");

        NumberFormat numberFormat = NumberFormat.getInstance();
        Number number;

        try {
            number = numberFormat.parse(stringNumber);
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new PaymentTransactionProcessingException();
        }

        Double value = number.doubleValue() / 100;

        return value;
    }

    private static boolean isStringLenghtValid(String string) {
        return string == null || (string.length() != 15 && string.length() != 18);
    }

}
