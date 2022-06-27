package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public interface CustomerMapper {

    /**
     * Snima podatke o korisniku
     *
     * @param customer
     */
    void saveCustomer(Customer customer);
    /**
     * Azurira podatke o korisniku
     * @param customer
     * @return
     */
    int updateCustomer(Customer customer);

    /**
     * Dohvata korisnika po prosledjenom id-u
     * @param customer
     * @return
     */
    Customer findCustomer(Customer customer);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param identityNumber
     * @return
     */
    Customer findCustomerByIdentityNumber(String identityNumber);

}