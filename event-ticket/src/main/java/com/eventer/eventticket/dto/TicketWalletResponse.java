package com.eventer.eventticket.dto;

import com.eventer.eventticket.utils.TicketWalletType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketWalletResponse {

    @JsonProperty
    private Status status;

    @JsonProperty
    private String details;

    @JsonProperty
    private List<Long> ticketIds;

    @JsonProperty
    private Long userId;

    @JsonProperty
    private TicketWalletType type;

}
