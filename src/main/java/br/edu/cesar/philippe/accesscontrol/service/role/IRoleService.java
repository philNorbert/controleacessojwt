package br.edu.cesar.philippe.accesscontrol.service.role;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public interface IRoleService {
    Role findOne(Long id) throws ResourceNotFoundException;

    List<Role> findAll();

    Role save(RoleDTO roleDTO) throws ResourceAlreadyExists;

    Role update(Long id, RoleDTO roleDTO) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
