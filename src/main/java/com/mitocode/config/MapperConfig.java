package com.mitocode.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class MapperConfig {
    @Bean(name = "defaultMapper")
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    @Bean(name = "medicMapper")
    public ModelMapper medicMapper(){

        ModelMapper modelMapper =new ModelMapper();
        return new ModelMapper();
    }

}
