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
public class CreateUserResponse implements Serializable {

    @JsonProperty
    Long userId;

    @JsonProperty
    Status status;

    @JsonProperty
    String details;

}