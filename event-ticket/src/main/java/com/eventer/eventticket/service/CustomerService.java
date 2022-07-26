package com.eventer.eventticket.service;

import com.eventer.eventticket.dao.mapper.CustomerMapper;
import com.eventer.eventticket.domain.Customer;
import com.eventer.eventticket.exception.CustomerException;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Random;

import static com.eventer.eventticket.exception.ApplicationException.ErrorType.*;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public Customer persistCustomerInfo(Customer customer){

        Customer existingCustomer = customerMapper.findCustomerByUsernameOrEmail(customer.getUsername(),
                customer.getEmail());

        if (existingCustomer != null) {
            throw new CustomerException(CUSTOMER_ALREADY_EXISTS);
        }
        customer.setRegistrationDate(Instant.now());
        customer.setStatus(Customer.CustomerStatus.Active);

        customerMapper.saveCustomer(customer);

        log.info("Kreiran: {}", customer);
        return customer;
    }

    @Transactional
    public void checkLoginInfo(Customer customerInfo){
        Customer customer = customerMapper.findCustomerByUsernameOrEmail(customerInfo.getUsername(),
                customerInfo.getEmail());
        if(customer == null){
            log.warn("Ne postoji korisnik za prosledjene parametre.");
            throw new CustomerException(NO_CUSTOMER_FOR_PARAMETERS);
        }
        if(!customerInfo.getPassword().equals(customer.getPassword())){
            log.warn("Ne valja pass");
            throw new CustomerException(BAD_CREDENTIALS);
        }
    }

}
