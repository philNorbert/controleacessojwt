package br.edu.cesar.philippe.accesscontrol.controller.system;

import br.edu.cesar.philippe.accesscontrol.model.System;
import br.edu.cesar.philippe.accesscontrol.request.SystemDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public interface ISystemResource {
    List<System> findAll();

    ResponseEntity<String> delete(final Long id);

    ResponseEntity<String> save(SystemDTO systemDTO) throws URISyntaxException;

    ResponseEntity<String> update(Long id, SystemDTO systemDTO) throws URISyntaxException;
}
