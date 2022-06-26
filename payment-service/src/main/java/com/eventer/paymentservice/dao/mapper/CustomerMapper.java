package com.eventer.paymentservice.dao.mapper;

import com.eventer.paymentservice.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public interface CustomerMapper {

    /**
     * Snima podatke o korisniku
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
     * @param id
     * @return
     */
    Customer findCustomer(Long id);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param identityNumber
     * @return
     */
    Customer findCustomerByIdentityNumber(String identityNumber);

    /**
     * Dohvata listu korisnika za prosledjeni username ili identityNumber
     * (rezultat bi trebalo da bude jedinstven,
     * ali desava se da postoje korisnici sa istim username-on)
     * @param request
     * @return
     */
  //  List<User> findPUserByUsernameAndIdentityNumber(CustomerRequest request);
}
