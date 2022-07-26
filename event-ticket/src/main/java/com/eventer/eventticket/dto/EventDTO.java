package com.eventer.eventticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    @JsonProperty
    private Long id;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant startDate;

    @NotNull
    @JsonProperty
    private Integer totalCapacity;

    @NotNull
    @JsonProperty
    private Double ticketPrice;

}