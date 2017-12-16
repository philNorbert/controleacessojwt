package br.edu.cesar.philippe.accesscontrol.controller;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.UsernameAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import br.edu.cesar.philippe.accesscontrol.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = ApiVersion.V1, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {
    
    private final IUserService userService;
    
    // BUSCAR TODOS OS USUARIOS QUE SATISFAÇAM A QUERY
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<User> findAll(@RequestParam(defaultValue = "", value = "q", required = false) final String query) {
        return userService.findAll(query);
    }
    
    // BUSCAR UM USUARIO POR ID
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public User findUser(@PathVariable final String id) {
        return userService.findOne(id);
    }
    
    // CADASTRAR UM USUÁRIO
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody final UserDTO userDTO) throws
            URISyntaxException {
        User user;
        try {
            user = userService.save(userDTO);
        } catch(UsernameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        
        return ResponseEntity.created(new URI(ApiVersion.V1 + "/users/" + user.getId())).build();
    }
    
    // ATUALIZAR UM USUÁRIO
    @PostMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestBody final UserDTO userDTO) throws
            URISyntaxException {
        User user;
        try {
            user = userService.save(userDTO);
        } catch(UsernameAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        
        return ResponseEntity.created(new URI(ApiVersion.V1 + "/users/" + user.getId())).build();
    }
    
}
