package br.edu.cesar.philippe.accesscontrol.controller.user;

import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserPasswordDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public interface IUserResource {
    List<User> findAll(String query);

    ResponseEntity<User> findUser(Long id);

    ResponseEntity<String> delete(Long id);

    ResponseEntity<String> saveUser(UserDTO userDTO) throws URISyntaxException;

    ResponseEntity<String> updateUser(Long id, UserDTO userDTO);

    ResponseEntity<String> updateRoleFromUser(Long id, List<RoleDTO> roles);

    ResponseEntity<String> updateGenerateNewPassword(Long id, UserPasswordDTO userPasswordDTO);

    ResponseEntity<String> updatePassword(Long id, UserPasswordDTO userPasswordDTO);
}
