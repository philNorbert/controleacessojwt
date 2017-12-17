package br.edu.cesar.philippe.accesscontrol.service.authority;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.repository.AuthorityRepository;
import br.edu.cesar.philippe.accesscontrol.request.AuthorityDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorityServiceImpl implements IAuthorityService {

    private AuthorityRepository authorityRepository;

    @Override
    public Authority findOne(Long id) throws ResourceNotFoundException {
        Authority authority = authorityRepository.findOne(id);
        if (authority == null) {
            throw new ResourceNotFoundException(String.format("Autorização (%s) não encontrada!", id));
        }
        return authority;
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority save(AuthorityDTO authorityDTO) throws ResourceAlreadyExists {
        String name = authorityDTO.getName();
        Authority authority = authorityRepository.findAuthorityByName(name);
        if (authority != null) {
            throw new ResourceAlreadyExists(String.format("Autorização (%s) já foi cadastrada!", name));
        }
        return authorityRepository.save(new Authority(name));
    }

    @Override
    public Authority update(Long id, AuthorityDTO authorityDTO) throws ResourceNotFoundException {
        Authority authority = authorityRepository.findOne(id);
        if (authority == null) {
            throw new ResourceNotFoundException(String.format("Autorização (%s) não encontrada!", id));
        }
        authority.setName(authorityDTO.getName());
        authorityRepository.save(authority);
        return authority;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Authority authority = this.findOne(id);
        authorityRepository.delete(authority);
    }

}
