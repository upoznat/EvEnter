package com.eventer.wallet.dto;

import com.eventer.wallet.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

    private String id;
    private TransactionStatus status;

    public PaymentResponseDTO(String externalId) {
        this.id= externalId;
    }

    public PaymentResponseDTO withStatus(TransactionStatus status){
        this.status = status;
        return this;
    }

}
