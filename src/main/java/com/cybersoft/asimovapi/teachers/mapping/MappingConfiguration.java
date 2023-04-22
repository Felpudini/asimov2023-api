package com.cybersoft.asimovapi.teachers.mapping;

import com.cybersoft.asimovapi.teachers.resource.SaveTeacherResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("teacherApiConfiguration")
public class MappingConfiguration {
    @Bean
    public teacherMapper teacherMapper(){
        return new teacherMapper();
    }
}
