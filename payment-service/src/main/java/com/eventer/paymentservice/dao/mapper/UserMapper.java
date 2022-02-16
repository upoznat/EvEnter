package com.eventer.paymentservice.dao.mapper;

import com.eventer.paymentservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    /**
     * Snima podatke o korisniku
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
     * @param id
     * @return
     */
    User findUser(Long id);

    /**
     * Dohvata korisnika po prosledjenom identityNumbery
     * @param identityNumber
     * @return
     */
    User findUserByIdentityNumber(String identityNumber);

    /**
     * Dohvata listu korisnika za prosledjeni username ili identityNumber
     * (rezultat bi trebalo da bude jedinstven,
     * ali desava se da postoje korisnici sa istim username-on)
     * @param request
     * @return
     */
  //  List<User> findPUserByUsernameAndIdentityNumber(UserRequest request);
}
