package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.domain.Customer;
import com.eventer.eventticket.dto.CreateCustomerResponse;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.eventer.eventticket.dto.Status.FAIL;
import static com.eventer.eventticket.dto.Status.SUCCESS;

@Slf4j
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("registerCustomer")
    public CreateCustomerResponse createCustomer(@Valid @RequestBody Customer newCustomerRequest){
        try {
            Customer customer = customerService.persistCustomerInfo(newCustomerRequest);

            return CreateCustomerResponse.builder().status(SUCCESS)
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return CreateCustomerResponse
                    .builder()
                    .status(FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }
}
