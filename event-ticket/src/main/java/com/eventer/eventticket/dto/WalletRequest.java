package com.eventer.eventticket.dto;


import com.eventer.eventticket.utils.TicketWalletType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequest {

    @NotBlank
    @JsonProperty
    private Long customerId;

    @NotBlank
    @JsonProperty
    private Double amount;

    @NotBlank
    @JsonProperty
    private List<Long> ticketIds;

    @NotBlank
    @JsonProperty
    private TicketWalletType type;

}
