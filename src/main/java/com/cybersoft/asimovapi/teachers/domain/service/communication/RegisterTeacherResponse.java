package com.cybersoft.asimovapi.teachers.domain.service.communication;

import com.cybersoft.asimovapi.shared.domain.service.communication.BaseResponse;
import com.cybersoft.asimovapi.teachers.resource.TeacherResource;

public class RegisterTeacherResponse extends BaseResponse<TeacherResource> {
    public RegisterTeacherResponse(String message) {
        super(message);
    }

    public RegisterTeacherResponse(TeacherResource resource) {
        super(resource);
    }
}
