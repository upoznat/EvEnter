package com.eventer.paymentservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class ExternalPaymentRequestDTO {

    @NotNull
    String transactionId;

    @NotNull
    @DecimalMin("1.00")
    Double amount;

    @NotNull
    String referenceNumber;

    @NotNull
    Date transactionDate;


}
