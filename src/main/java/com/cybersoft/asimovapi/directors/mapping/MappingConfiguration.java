package com.cybersoft.asimovapi.directors.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("directorMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public DirectorMapper directorMapper() { return new DirectorMapper(); }
}
