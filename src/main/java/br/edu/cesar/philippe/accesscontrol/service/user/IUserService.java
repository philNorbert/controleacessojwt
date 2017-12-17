package br.edu.cesar.philippe.accesscontrol.service.user;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserPasswordDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import javax.validation.ValidationException;
import java.util.List;

public interface IUserService {
    User findOne(Long id) throws ResourceNotFoundException;

    List<User> findAll(String queryBusca);

    User save(UserDTO userDTO) throws ResourceAlreadyExists;

    User update(Long id, UserDTO userDTO) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

    User updateRoles(Long id, List<RoleDTO> rolesDTO) throws ResourceNotFoundException;

    User updatePassword(Long id, UserPasswordDTO userPasswordDTO) throws ResourceNotFoundException,
            ValidationException;

    String updateGenerateNewPassword(Long id) throws ResourceNotFoundException;
}
