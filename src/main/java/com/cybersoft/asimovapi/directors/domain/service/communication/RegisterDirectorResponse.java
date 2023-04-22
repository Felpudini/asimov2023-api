package com.cybersoft.asimovapi.directors.domain.service.communication;

import com.cybersoft.asimovapi.directors.resource.DirectorResource;
import com.cybersoft.asimovapi.shared.domain.service.communication.BaseResponse;

public class RegisterDirectorResponse extends BaseResponse<DirectorResource> {
    public RegisterDirectorResponse(String message) {
        super(message);
    }

    public RegisterDirectorResponse(DirectorResource resource) {
        super(resource);
    }
}
