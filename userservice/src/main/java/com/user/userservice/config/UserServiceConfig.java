package com.user.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
public class UserServiceConfig {

    @Bean
    public ObjectMapper objectMapperInit(){
        return new ObjectMapper();
    }
}
