package com.cybersoft.asimovapi.directors.domain.service.communication;

import com.cybersoft.asimovapi.directors.resource.AuthenticateDirectorResource;
import com.cybersoft.asimovapi.shared.domain.service.communication.BaseResponse;

public class AuthenticateDirectorResponse extends BaseResponse<AuthenticateDirectorResource> {
    public AuthenticateDirectorResponse(String message) {
        super(message);
    }

    public AuthenticateDirectorResponse(AuthenticateDirectorResource resource) {
        super(resource);
    }
}
