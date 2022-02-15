package com.eventer.paymentservice.dto;

import com.eventer.paymentservice.domain.PaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PaymentResponseDTO {

    private Long id;
    private PaymentStatus status;
}
