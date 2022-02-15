package com.eventer.paymentservice.dto;

import java.util.Date;

import com.eventer.paymentservice.utils.ToStringHelper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    @JsonProperty("lcMemberId")
    private Long userId;

    @JsonProperty("identityNumber")
    private String userName;

    @JsonProperty("identityNumber")
    private String identityNumber;



    @Override
    public String toString() {
        return ToStringHelper.toString(this);
    }

}


