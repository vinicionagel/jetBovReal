package br.com.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
public class ModelMapperUsuarioConfig implements Serializable {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
