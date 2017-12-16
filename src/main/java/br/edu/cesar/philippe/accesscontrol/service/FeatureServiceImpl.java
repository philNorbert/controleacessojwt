package br.edu.cesar.philippe.accesscontrol.service;

import br.edu.cesar.philippe.accesscontrol.exception.UsernameAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.model.enums.RoleNames;
import br.edu.cesar.philippe.accesscontrol.repository.RoleRepository;
import br.edu.cesar.philippe.accesscontrol.repository.UserRepository;
import br.edu.cesar.philippe.accesscontrol.repository.UserSpecificationsBuilder;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder enc = new BCryptPasswordEncoder();

    public User findOne(String username) {
        User usu = userRepository.findByUsername(username);
        if (usu == null) {
            throw new UsernameNotFoundException(String.format("Usuário (%s) não encontrado!",username));
        }
        return usu;
    }
    
    public List<User> findAll(String queryBusca) {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)([:<>])(\\w*)");
        Matcher matcher = pattern.matcher(queryBusca + ",");
    
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
    
        Specification<User> spec = builder.build();
        return userRepository.findAll(spec);
    }

    public User save(final UserDTO request) throws UsernameAlreadyExists{

        final String encodedPassword = enc.encode(request.getPassword());
        
        User usu = userRepository.findByUsername(request.getUsername());
        if(usu != null){
            throw new UsernameAlreadyExists("O usuário com o login: " + request.getUsername() + " já existe!");
        }
    
        Role defaultRole = roleRepository.findRoleByName(RoleNames.ROLE_USER.name());
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setRoles(Arrays.asList(defaultRole));
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(Boolean.TRUE);
 
        return userRepository.save(user);
    }
    
 /*   public User update(final UserDTO request) throws UsernameAlreadyExists{
        
        final String encodedPassword = enc.encode(request.getPassword());
        
        User usu = userRepository.findByUsername(request.getUsername());
        if(usu == null){
            throw new UsernameNo("O usuário com o login: " + request.getUsername() + " já existe!");
        }
        
        Role defaultRole = roleRepository.findRoleByName(RoleNames.ROLE_USER.name());
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setEmail(request.getEmail());
        user.setRoles(Arrays.asList(defaultRole));
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(Boolean.TRUE);
        
        return userRepository.save(user);
    }*/
    
    
    
}
