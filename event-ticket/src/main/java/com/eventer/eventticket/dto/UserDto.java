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
public class UserDto {

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
