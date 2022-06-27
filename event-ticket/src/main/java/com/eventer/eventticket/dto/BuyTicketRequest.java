package com.eventer.eventticket.dto;

import com.eventer.eventticket.utils.TicketWalletType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketRequest {

        @NotBlank
        @JsonProperty
        private Long customerId;

        @NotBlank
        @JsonProperty
        private Long eventId;

        @NotBlank
        @JsonProperty
        private Integer numberOfTickets;

}
