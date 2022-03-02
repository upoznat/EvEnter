package com.eventer.eventticket.service;

import com.eventer.eventticket.dao.mapper.UserMapper;
import com.eventer.eventticket.domain.User;
import com.eventer.eventticket.dto.UserDto;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.eventticket.exception.ApplicationException.ErrorType.NO_USER_FOR_PARAMETERS;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User persistUserInfo(User user){

        User existingUser = userMapper.findUser(user);

        if (existingUser == null) {
            userMapper.saveUser(user);
        }
        else {
            userMapper.updateUser(user);
        }

        return user;
    }

    public User getUser(User userInfo){
        User user = userMapper.findUser(userInfo);
        if(user == null){
            log.warn("Ne postoji korisnik za prosledjene parametre.");
            throw new EventTicketProcessingException(NO_USER_FOR_PARAMETERS);
        }
        return user;
    }

}
