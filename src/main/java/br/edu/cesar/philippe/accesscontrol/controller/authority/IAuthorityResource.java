package br.edu.cesar.philippe.accesscontrol.controller.authority;

import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.request.AuthorityDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public interface IAuthorityResource {
    List<Authority> findAll();

    ResponseEntity<String> delete(Long id);

    ResponseEntity<String> save(AuthorityDTO authorityDTO) throws URISyntaxException;

    ResponseEntity<String> update(Long id, AuthorityDTO AuthorityDTO) throws URISyntaxException;
}
