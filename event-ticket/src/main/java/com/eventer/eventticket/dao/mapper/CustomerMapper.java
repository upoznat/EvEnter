package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.Customer;
import org.apache.ibatis.annotations.Param;
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
     * @return
     */
    Customer findCustomer(Long id);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param username - korisnisko ime
     * @param email - email adresa korisnika
     * @return vraca korisnika
     */
    Customer findCustomerByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param identityNumber
     * @return
     */
    Customer findCustomerByIdentityNumber(String identityNumber);

}