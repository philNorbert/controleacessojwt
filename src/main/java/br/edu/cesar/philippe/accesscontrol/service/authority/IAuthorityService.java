package br.edu.cesar.philippe.accesscontrol.service.authority;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.request.AuthorityDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public interface IAuthorityService {
    Authority findOne(Long id) throws ResourceNotFoundException;

    List<Authority> findAll();

    Authority save(AuthorityDTO request) throws ResourceAlreadyExists;

    Authority update(Long id, AuthorityDTO request) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
