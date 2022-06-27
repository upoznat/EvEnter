package com.eventer.eventticket.domain;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Customer {

    private Long id;
    private String username;
    private String password;
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String address;
    private Instant registrationDate;
    private String email;
    private CustomerStatus status;


    @Builder
    public Customer(Long id, String username, String password, String identityNumber, String firstName, String lastName, String address, Instant registrationDate, String email, CustomerStatus status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identityNumber = identityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.registrationDate = registrationDate;
        this.email = email;
        this.status = status;
    }



    public enum CustomerStatus {
        Active,
        Deleted;
    }

}
