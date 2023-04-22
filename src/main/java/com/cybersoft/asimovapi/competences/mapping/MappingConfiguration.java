package com.cybersoft.asimovapi.competences.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("competenceMappingConfiguration")
public class MappingConfiguration {

    @Bean
    public CompetenceMapper competenceMapper() { return new CompetenceMapper(); }
}
