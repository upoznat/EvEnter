package com.eventer.wallet.controllers;

import com.eventer.wallet.amqp.rabbit.PaymentResponseSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test")
@RestController
public class TestRest {

    @Autowired
    private PaymentResponseSender sender;

    @GetMapping("/alo")
    public String send(){

        return "ok. done";
    }


}
