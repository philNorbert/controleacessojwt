package br.edu.cesar.philippe.accesscontrol.controller.role;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import br.edu.cesar.philippe.accesscontrol.service.role.IRoleService;
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
@RequestMapping(value = ApiVersion.V1 + "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource implements IRoleResource {

    private IRoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Role> findRole(@PathVariable Long id) {
        Role role;
        try {
            role = roleService.findOne(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            roleService.delete(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody RoleDTO roleDTO) throws
            URISyntaxException {
        Role role;
        try {
            role = roleService.save(roleDTO);
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/role/" + role.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody RoleDTO roleDTO) throws
            URISyntaxException {
        Role role;
        try {
            role = roleService.update(id, roleDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/role/" + role.getId())).build();
    }

}
