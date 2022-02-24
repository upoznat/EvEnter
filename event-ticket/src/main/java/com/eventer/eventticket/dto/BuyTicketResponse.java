package com.eventer.eventticket.dto;

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
public class BuyTicketResponse {

    @JsonProperty
    private Status status;

    @JsonProperty
    private String details;

    @JsonProperty
    private List<Long> ticketIds;

}
