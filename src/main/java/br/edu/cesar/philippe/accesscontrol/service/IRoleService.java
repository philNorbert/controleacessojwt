package br.edu.cesar.philippe.accesscontrol.service;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;

import java.util.List;

public interface IRoleService {
    Role findOne(String name);
    List<Role> findAll();
    Role save(final RoleDTO roleDTO) throws ResourceAlreadyExists;
}
