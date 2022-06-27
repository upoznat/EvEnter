package com.eventer.eventticket.dto;

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
public class CustomerDto {

    @NotBlank
    @JsonProperty
    private String username;

    @NotBlank
    @JsonProperty
    private String password;

    @NotBlank
    @JsonProperty
    private String identityNumber;

    @NotBlank
    @JsonProperty
    private String firstName;

    @NotBlank
    @JsonProperty
    private String lasttName;

    @NotBlank
    @JsonProperty
    private String address;

    @NotBlank
    @JsonProperty
    private String email;

}
