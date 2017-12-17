package br.edu.cesar.philippe.accesscontrol.controller.system;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.System;
import br.edu.cesar.philippe.accesscontrol.request.SystemDTO;
import br.edu.cesar.philippe.accesscontrol.service.system.ISystemService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(value = ApiVersion.V1 + "/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemResource implements ISystemResource {

    private ISystemService systemService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<System> findAll() {
        return systemService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<System> findSystem(@PathVariable Long id) {
        System System;
        try {
            System = systemService.findOne(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(System);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            systemService.delete(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody SystemDTO systemDTO) throws
            URISyntaxException {
        System System;
        try {
            System = systemService.save(systemDTO);
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/system/" + System.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody SystemDTO systemDTO) throws
            URISyntaxException {
        System System;
        try {
            System = systemService.update(id, systemDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/System/" + System.getId())).build();
    }

}