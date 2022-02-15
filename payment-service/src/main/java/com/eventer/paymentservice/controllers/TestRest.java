package com.eventer.paymentservice.controllers;

import com.eventer.paymentservice.amqp.rabbit.PaymentRequestSender;
import com.eventer.paymentservice.domain.PaymentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test")
@RestController
public class TestRest {

    @Autowired
    private PaymentRequestSender sender;

    @GetMapping("/alo")
    public String send(){
        sender.send(new PaymentTransaction());
        return "ok. done";
    }


}
