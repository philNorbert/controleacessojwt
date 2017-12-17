package br.edu.cesar.philippe.accesscontrol.controller.authority;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.request.AuthorityDTO;
import br.edu.cesar.philippe.accesscontrol.service.authority.IAuthorityService;
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
@RequestMapping(value = ApiVersion.V1 + "/authority", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorityResource implements IAuthorityResource {

    private IAuthorityService authorityService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<Authority> findAll() {
        return authorityService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Authority> findAuthority(@PathVariable Long id) {
        Authority authority;
        try {
            authority = authorityService.findOne(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(authority);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            authorityService.delete(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody AuthorityDTO authorityDTO) throws
            URISyntaxException {
        Authority authority;
        try {
            authority = authorityService.save(authorityDTO);
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/authority/" + authority.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody AuthorityDTO AuthorityDTO) throws
            URISyntaxException {
        Authority authority;
        try {
            authority = authorityService.update(id, AuthorityDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/authority/" + authority.getId())).build();
    }

}