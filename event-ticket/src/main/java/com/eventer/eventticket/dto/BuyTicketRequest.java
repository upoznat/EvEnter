package com.eventer.eventticket.dto;

import com.eventer.eventticket.utils.TicketWalletType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketRequest {

        @NotNull
        @JsonProperty
        private Long customerId;

        @NotNull
        @JsonProperty
        private Long eventId;

        @NotNull
        @JsonProperty
        private Integer numberOfTickets;

}
