package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.domain.Customer;
import com.eventer.eventticket.dto.CreateCustomerResponse;
import com.eventer.eventticket.dto.CustomResponse;
import com.eventer.eventticket.exception.CustomerException;
import com.eventer.eventticket.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.eventer.eventticket.dto.Status.FAIL;
import static com.eventer.eventticket.dto.Status.SUCCESS;

@Slf4j
@RequestMapping("/customer")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public CreateCustomerResponse createCustomer(@Valid @RequestBody Customer newCustomerRequest){
        try {
            Customer customer = customerService.persistCustomerInfo(newCustomerRequest);

            return CreateCustomerResponse.builder()
                    .customerId(customer.getId())
                    .status(SUCCESS)
                    .details("").build();

        } catch (CustomerException e) {
            return CreateCustomerResponse
                    .builder()
                    .status(FAIL)
                    .details(e.getErrorType().getDescription())
                    .build();
        }
    }

    @PostMapping("/login")
    public CustomResponse login(@Valid @RequestBody Customer loginInfo){
        try {
            customerService.checkLoginInfo(loginInfo);

            return CustomResponse.builder()
                    .status(SUCCESS)
                    .details("").build();

        } catch (CustomerException e) {
            return CreateCustomerResponse
                    .builder()
                    .status(FAIL)
                    .details(e.getErrorType().getDescription())
                    .build();
        }
    }
}
