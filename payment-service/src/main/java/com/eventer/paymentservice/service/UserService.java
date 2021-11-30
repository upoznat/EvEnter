package com.eventer.paymentservice.service;

import com.eventer.paymentservice.dao.mapper.UserMapper;
import com.eventer.paymentservice.domain.User;
import com.eventer.paymentservice.dto.UserInfoDTO;
import com.eventer.paymentservice.exception.PaymentTransactionProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.paymentservice.exception.ApplicationException.ErrorType.NO_PLAYER_FOR_PARAMETERS;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public User persistPlayerInfo(UserInfoDTO userInfo){
        User player = User.builder()
                .id(userInfo.getUserId())
                .identityNumber(userInfo.getIdentityNumber())
                .username(userInfo.getUserName())
                .build();

        User existingUser = userMapper.findUser(userInfo.getUserId());

        if (existingUser == null) {
            userMapper.saveUser(player);
        }
        else {
            userMapper.updateUser(player);
        }

        return player;
    }

    public User getUser(UserInfoDTO userInfo){
        User user = userMapper.findUser(userInfo.getUserId());
        if(user == null){
            log.warn("Ne postoji igrac za prosledjene parametre.");
            throw new PaymentTransactionProcessingException(NO_PLAYER_FOR_PARAMETERS);
        }
        return user;
    }

}
