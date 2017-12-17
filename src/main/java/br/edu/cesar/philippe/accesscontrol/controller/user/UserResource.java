package br.edu.cesar.philippe.accesscontrol.controller.user;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserPasswordDTO;
import br.edu.cesar.philippe.accesscontrol.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = ApiVersion.V1 + "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource implements IUserResource {

    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public List<User> findAll(@RequestParam(defaultValue = "", value = "q", required = false) String query) {
        return userService.findAll(query);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public ResponseEntity<User> findUser(@PathVariable Long id) {
        User user;
        try {
            user = userService.findOne(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    @Override
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    @Override
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) throws
            URISyntaxException {
        User user;
        try {
            user = userService.save(userDTO);
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/user/" + user.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userService.update(id, userDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public ResponseEntity<String> updateRoleFromUser(@PathVariable Long id, @RequestBody List<RoleDTO> roles) {
        try {
            userService.updateRoles(id, roles);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/gerarSenha")
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTHORITY','USER_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public ResponseEntity<String> updateGenerateNewPassword(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
        String newPassword;
        try {
            newPassword = userService.updateGenerateNewPassword(id);
        } catch (ResourceNotFoundException | ValidationException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok().body(newPassword);
    }

    @PostMapping("/{id}/atualizarSenha")
    @PreAuthorize("hasAnyAuthority('ADMIN_AUTHORITY','USER_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Override
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO userPasswordDTO) {
        try {
            userService.updatePassword(id, userPasswordDTO);
        } catch (ResourceNotFoundException | ValidationException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok().build();
    }

}