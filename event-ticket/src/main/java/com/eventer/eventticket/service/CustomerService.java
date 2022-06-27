package com.eventer.eventticket.service;

import com.eventer.eventticket.dao.mapper.CustomerMapper;
import com.eventer.eventticket.domain.Customer;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.eventticket.exception.ApplicationException.ErrorType.NO_CUSTOMER_FOR_PARAMETERS;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public Customer persistCustomerInfo(Customer customer){

        Customer existingCustomer = customerMapper.findCustomer(customer);

        if (existingCustomer == null) {
            customerMapper.saveCustomer(customer);
        }
        else {
            customerMapper.updateCustomer(customer);
        }

        return customer;
    }

    public Customer getCustomer(Customer customerInfo){
        Customer customer = customerMapper.findCustomer(customerInfo);
        if(customer == null){
            log.warn("Ne postoji korisnik za prosledjene parametre.");
            throw new EventTicketProcessingException(NO_CUSTOMER_FOR_PARAMETERS);
        }
        return customer;
    }

}
