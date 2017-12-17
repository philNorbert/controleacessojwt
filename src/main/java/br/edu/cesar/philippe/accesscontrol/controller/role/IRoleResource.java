package br.edu.cesar.philippe.accesscontrol.controller.role;

import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;
import java.util.List;

public interface IRoleResource {
    List<Role> findAll();

    ResponseEntity<Role> findRole(@PathVariable final Long id);

    ResponseEntity<String> delete(@PathVariable final Long id);

    ResponseEntity<String> save(@RequestBody final RoleDTO roleDTO) throws URISyntaxException;

    ResponseEntity<String> update(@PathVariable final Long id, @RequestBody final RoleDTO roleDTO) throws
            URISyntaxException;
}
