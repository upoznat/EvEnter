package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    /**
     * Snima podatke o korisniku
     *
     * @param user
     */
    void saveUser(User user);
    /**
     * Azurira podatke o korisniku
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * Dohvata korisnika po prosledjenom id-u
     * @param user
     * @return
     */
    User findUser(User user);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param identityNumber
     * @return
     */
    User findUserByIdentityNumber(String identityNumber);

}