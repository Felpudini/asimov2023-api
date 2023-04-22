package com.cybersoft.asimovapi.teachers.mapping;

import com.cybersoft.asimovapi.security.domain.model.entity.Role;
import com.cybersoft.asimovapi.teachers.domain.model.Teacher;
import com.cybersoft.asimovapi.teachers.resource.SaveTeacherResource;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import com.cybersoft.asimovapi.shared.mapping.EnhancedModelMapper;
import java.util.List;
public class teacherMapper {
    
    @Autowired
    EnhancedModelMapper mapper;

    Converter<Role, String> roleToString = new AbstractConverter<>() {
        @Override
        protected String convert(Role role) {
            return role == null ? null : role.getName().name();
        }
    };

    public TeacherResource toResource(Teacher model){
        mapper.addConverter(roleToString);
        return mapper.map(model,TeacherResource.class);
    }

    public List<TeacherResource> modelListToResource(List<Teacher> modelList){
        mapper.addConverter(roleToString);
        return mapper.mapList(modelList, TeacherResource.class);
    }

    public Teacher toModel(SaveTeacherResource resource) {
        return mapper.map(resource, Teacher.class);
    }
}
