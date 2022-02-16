package com.eventer.paymentservice.service;

import com.eventer.paymentservice.dao.mapper.UserMapper;
import com.eventer.paymentservice.domain.User;
import com.eventer.paymentservice.dto.UserInfoDTO;
import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.paymentservice.exception.ApplicationException.ErrorType.NO_USER_FOR_PARAMETERS;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User persistUserInfo(UserInfoDTO userInfo){
        User user = User.builder()
                .id(userInfo.getUserId())
                .identityNumber(userInfo.getIdentityNumber())
                .username(userInfo.getUserName())
                .build();

        User existingUser = userMapper.findUser(userInfo.getUserId());

        if (existingUser == null) {
            userMapper.saveUser(user);
        }
        else {
            userMapper.updateUser(user);
        }

        return user;
    }

    public User getUser(UserInfoDTO userInfo){
        User user = userMapper.findUser(userInfo.getUserId());
        if(user == null){
            log.warn("Ne postoji korisnik za prosledjene parametre.");
            throw new PaymentTransactionProcessingException(NO_USER_FOR_PARAMETERS);
        }
        return user;
    }

}
