package com.eventer.eventticket;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eventer.eventticket.dao.mapper")
public class EventTicketApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventTicketApplication.class, args);
	}

}
