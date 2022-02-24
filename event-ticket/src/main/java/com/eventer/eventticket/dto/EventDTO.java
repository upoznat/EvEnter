package com.eventer.eventticket.dto;

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
public class EventDTO {

    @NotBlank
    @JsonProperty
    private String name;

    @NotBlank
    @JsonProperty
    private String location;

    @NotBlank
    @JsonProperty
    private String address;

    @NotNull
    @JsonProperty
    private Integer totalCapacity;

    @NotNull
    @JsonProperty
    private Double ticketPrice;

}