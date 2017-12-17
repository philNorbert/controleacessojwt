package br.edu.cesar.philippe.accesscontrol.service.user;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.model.enums.RoleNames;
import br.edu.cesar.philippe.accesscontrol.repository.RoleRepository;
import br.edu.cesar.philippe.accesscontrol.repository.UserRepository;
import br.edu.cesar.philippe.accesscontrol.repository.UserSpecificationsBuilder;
import br.edu.cesar.philippe.accesscontrol.request.RoleDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;
import br.edu.cesar.philippe.accesscontrol.request.UserPasswordDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder enc;

    @Override
    public User findOne(Long id) throws ResourceNotFoundException {
        User usu = userRepository.findOne(id);
        if (usu == null) {
            throw new ResourceNotFoundException(String.format("Usuário (%s) não encontrado!", id));
        }
        return usu;
    }


    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        User usu = this.findOne(id);
        userRepository.delete(usu);
    }

    @Override
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

    @Override
    public User save(UserDTO userDTO) throws ResourceAlreadyExists {
        String encodedPassword = enc.encode(userDTO.getPassword());

        User usu = userRepository.findByUsername(userDTO.getUsername());
        if (usu != null) {
            throw new ResourceAlreadyExists("O usuário com o login: " + userDTO.getUsername() + " já existe!");
        }

        Role defaultRole = roleRepository.findRoleByName(RoleNames.ROLE_USER.name());

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRoles(Arrays.asList(defaultRole));
        user.setLastPasswordResetDate(new Date());
        user.setEnabled(Boolean.TRUE);

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserDTO userDTO) throws ResourceNotFoundException {
        User user = this.findOne(id);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(userDTO.getEnabled());

        return userRepository.save(user);
    }

    @Override
    public User updateRoles(Long id, List<RoleDTO> rolesDTO) throws ResourceNotFoundException {
        List<Role> roles = this.findRoles(rolesDTO);
        User user = this.findOne(id);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(Long id, UserPasswordDTO userPasswordDTO) throws ResourceNotFoundException,
            ValidationException {
        User user = this.findOne(id);
        if (!enc.matches(userPasswordDTO.getConfirmPassword(), user.getPassword())) {
            throw new ValidationException("A senha de confirmacao não é a correta");
        }
        user.setPassword(enc.encode(userPasswordDTO.getNewPassword()));
        return userRepository.save(user);
    }

    @Override
    public String updateGenerateNewPassword(Long id) throws ResourceNotFoundException {
        User user = this.findOne(id);
        String newPassword = generatePassword();
        user.setPassword(enc.encode(newPassword));
        userRepository.save(user);

        return newPassword;
    }

    private List<Role> findRoles(List<RoleDTO> rolesDTO) {
        List<String> rolesNames = rolesDTO.stream()
                .map(RoleDTO::getName)
                .collect(Collectors.toList());
        return roleRepository.findRolesByNameIsIn(rolesNames);
    }

    private static String generatePassword() {
        StringBuilder newPassword = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i <= 5; i++) {
            char a = (char) (65 + (rand.nextInt() % 57));
            newPassword.append(a);
        }
        return newPassword.toString();
    }

}
