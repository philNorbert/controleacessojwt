package br.edu.cesar.philippe.accesscontrol.service;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.exception.UsernameAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.repository.AuthorityRepository;
import br.edu.cesar.philippe.accesscontrol.repository.RoleRepository;
import br.edu.cesar.philippe.accesscontrol.request.AuthorityDTO;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService{

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    public Role findOne(String name) {
        Role role = roleRepository.findRoleByName(name);
        if (role == null) {
            throw new ResourceNotFoundException(String.format("Perfil (%s) não encontrado!",name));
        }
        return role;
    }
    
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role save(final RoleDTO roleDTO) throws ResourceAlreadyExists {
        String name = roleDTO.getName();
        Role role = roleRepository.findRoleByName(name);
        if(role != null) {
            throw new ResourceAlreadyExists(String.format("Perfil (%s) já foi cadastrado!",name));
        }
        return roleRepository.save(new Role(name));
    }
    
    public Role update(Long id, final RoleDTO request) throws UsernameAlreadyExists{
        
        List<String> authNames = request.getAuthorizations().stream()
                .map(AuthorityDTO::getName)
                .collect(Collectors.toList());
        List<Authority> authorities = authorityRepository.findAuthoritiesByNameIsIn(authNames);
        
        Role role = roleRepository.getOne(id);
        role.setName();
        
        return
    }
    
    
    
}
