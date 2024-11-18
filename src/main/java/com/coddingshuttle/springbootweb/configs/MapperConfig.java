package com.coddingshuttle.springbootweb.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper(){ //Factory Method
        return new ModelMapper();
    }
}
