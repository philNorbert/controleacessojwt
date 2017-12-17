package br.edu.cesar.philippe.accesscontrol.service.role;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
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

    private RoleRepository roleRepository;
    private AuthorityRepository authorityRepository;

    @Override
    public Role findOne(Long id) throws ResourceNotFoundException {
        Role role = roleRepository.findOne(id);
        if (role == null) {
            throw new ResourceNotFoundException(String.format("Perfil (%s) não encontrado!", id));
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Role role = this.findOne(id);
        roleRepository.delete(role);
    }

    @Override
    public Role save(RoleDTO roleDTO) throws ResourceAlreadyExists {
        String name = roleDTO.getName();
        Role role = roleRepository.findRoleByName(name);
        if(role != null) {
            throw new ResourceAlreadyExists(String.format("Perfil (%s) já foi cadastrado!",name));
        }
        return roleRepository.save(new Role(name));
    }

    @Override
    public Role update(Long id, RoleDTO roleDTO) throws ResourceNotFoundException {

        List<String> authNames = roleDTO.getAuthorizations().stream()
                .map(AuthorityDTO::getName)
                .collect(Collectors.toList());
        List<Authority> authorities = authorityRepository.findAuthoritiesByNameIsIn(authNames);

        Role role = roleRepository.findOne(id);
        if (role == null) {
            throw new ResourceNotFoundException(String.format("Perfil (%s) não encontrado!", id));
        }

        role.setName(roleDTO.getName());
        role.setAuthorities(authorities);
        roleRepository.save(role);

        return role;
    }
    
    
    
}
