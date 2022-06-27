package com.eventer.eventticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerResponse implements Serializable {

    @JsonProperty
    Long customerId;

    @JsonProperty
    Status status;

    @JsonProperty
    String details;

}