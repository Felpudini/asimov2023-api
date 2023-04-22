package com.cybersoft.asimovapi.teachers.domain.service.communication;

import com.cybersoft.asimovapi.shared.domain.service.communication.BaseResponse;
import com.cybersoft.asimovapi.teachers.resource.AuthenticateTeacherResource;

public class AuthenticateTeacherResponse extends BaseResponse<AuthenticateTeacherResource> {
    public AuthenticateTeacherResponse(String message) {
        super(message);
    }

    public AuthenticateTeacherResponse(AuthenticateTeacherResource resource) {
        super(resource);
    }
}
