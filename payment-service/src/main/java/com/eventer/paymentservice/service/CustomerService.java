package com.eventer.paymentservice.service;

import com.eventer.paymentservice.dao.mapper.CustomerMapper;
import com.eventer.paymentservice.domain.Customer;
import com.eventer.paymentservice.dto.CustomerInfoDTO;
import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.paymentservice.exception.ApplicationException.ErrorType.NO_CUSTOMER_FOR_PARAMETERS;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Transactional
    public Customer persistUserInfo(CustomerInfoDTO customerInfo){
        Customer customer = Customer.builder()
                .id(customerInfo.getCustomerId())
                .identityNumber(customerInfo.getIdentityNumber())
                .username(customerInfo.getUserName())
                .build();

        Customer existingCustomer = customerMapper.findCustomer(customerInfo.getCustomerId());

        if (existingCustomer == null) {
            customerMapper.saveCustomer(customer);
        }
        else {
            customerMapper.updateCustomer(customer);
        }

        return customer;
    }

    public Customer getCustomer(CustomerInfoDTO customerInfo){
        Customer customer = customerMapper.findCustomer(customerInfo.getCustomerId());
        if(customer == null){
            log.warn("Ne postoji korisnik za prosledjene parametre.");
            throw new PaymentTransactionProcessingException(NO_CUSTOMER_FOR_PARAMETERS);
        }
        return customer;
    }

}
