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

}