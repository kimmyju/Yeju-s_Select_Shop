package com.sparta.week4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing //tlrks wkehd qusruddl rksmdgkehfhr gka
@SpringBootApplication //스프링부트임을 선언
public class Week4Application {

    public static void main(String[] args) {
        SpringApplication.run(Week4Application.class, args);

    }

}
