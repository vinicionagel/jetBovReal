package br.com.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperPermissaoConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
