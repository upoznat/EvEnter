package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.domain.User;
import com.eventer.eventticket.domain.User.UserStatus;
import com.eventer.eventticket.dto.CreateEventResponse;
import com.eventer.eventticket.dto.CreateUserResponse;
import com.eventer.eventticket.dto.Status;
import com.eventer.eventticket.dto.UserDto;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.eventer.eventticket.domain.User.UserStatus.Active;
import static com.eventer.eventticket.dto.Status.FAIL;
import static com.eventer.eventticket.dto.Status.SUCCESS;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("registerUser")
    public CreateUserResponse createUser(@Valid @RequestBody User newUserRequest){
        try {
            User user = userService.persistUserInfo(newUserRequest);

            return CreateUserResponse.builder().status(SUCCESS)
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return CreateUserResponse
                    .builder()
                    .status(FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }
}
