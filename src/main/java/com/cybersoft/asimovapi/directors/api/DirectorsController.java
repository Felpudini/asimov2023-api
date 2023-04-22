package com.cybersoft.asimovapi.directors.api;

import com.cybersoft.asimovapi.directors.domain.service.DirectorService;
import com.cybersoft.asimovapi.directors.domain.service.communication.RegisterDirectorRequest;
import com.cybersoft.asimovapi.directors.mapping.DirectorMapper;
import com.cybersoft.asimovapi.directors.resource.CreateDirectorResource;
import com.cybersoft.asimovapi.directors.resource.DirectorResource;
import com.cybersoft.asimovapi.directors.resource.UpdateDirectorResource;
import com.cybersoft.asimovapi.security.domain.service.communication.AuthenticateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/directors")
public class DirectorsController {

    @Autowired
    private DirectorService directorService;

    @Autowired
    private DirectorMapper mapper;

    @GetMapping
    public List<DirectorResource> getAllDirectors() { return mapper.modelListToResource(directorService.getAll()); }

    @GetMapping("{directorId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public DirectorResource getDirectorById(@PathVariable("directorId") Long directorId) {
        return mapper.toResource(directorService.getById(directorId));
    }
    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticateRequest request) {
        return directorService.authenticate(request);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDirectorRequest request) {
        return directorService.register(request);
    }

    @PutMapping("{directorId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public DirectorResource updateDirector(@PathVariable Long directorId, @RequestBody UpdateDirectorResource request) {
        return mapper.toResource(directorService.update(directorId, mapper.toModel(request)));
    }

    @DeleteMapping("{directorId}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<?> deleteDirector(@PathVariable Long directorId) {
        return directorService.delete(directorId);
    }
}
