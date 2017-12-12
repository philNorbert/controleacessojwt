package br.edu.cesar.philippe.contoleacesso.security.service;

import br.edu.cesar.philippe.contoleacesso.security.JwtUserFactory;
import br.edu.cesar.philippe.contoleacesso.security.model.User;
import br.edu.cesar.philippe.contoleacesso.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Não existe usuário com o username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
