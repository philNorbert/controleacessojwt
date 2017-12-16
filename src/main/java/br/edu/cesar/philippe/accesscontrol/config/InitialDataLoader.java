package br.edu.cesar.philippe.accesscontrol.config;

import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.model.enums.RoleNames;
import br.edu.cesar.philippe.accesscontrol.repository.AuthorityRepository;
import br.edu.cesar.philippe.accesscontrol.repository.RoleRepository;
import br.edu.cesar.philippe.accesscontrol.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final LocalDateTime now = LocalDateTime.now();
    private boolean alreadySetup = false;
    
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
        if (alreadySetup) {
            return;
        }
    
        Authority adminAuthority = createAuthorityIfNotFound("ADMIN_AUTHORITY");
        Authority userAuthority = createAuthorityIfNotFound("USER_AUTHORITY");
        
        Role adminRole = createRoleIfNotFound(RoleNames.ROLE_ADMIN.name(), Arrays.asList(adminAuthority));
        Role userRole = createRoleIfNotFound(RoleNames.ROLE_USER.name(), Arrays.asList(userAuthority));
    
        ZonedDateTime zdt = now.atZone(ZoneId.systemDefault());
        
        // Criando usuario admin
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setEmail("admin@admin.com.br");
        adminUser.setRoles(Arrays.asList(adminRole));
        adminUser.setLastPasswordResetDate(Date.from(zdt.toInstant()));
        adminUser.setEnabled(Boolean.TRUE);
        userRepository.save(adminUser);
    
        // Criando usuario default
        User defaultUser = new User();
        defaultUser.setUsername("user");
        defaultUser.setPassword(passwordEncoder.encode("user"));
        defaultUser.setFirstName("Default");
        defaultUser.setLastName("User");
        defaultUser.setEmail("user@user.com.br");
        defaultUser.setRoles(Arrays.asList(userRole));
        defaultUser.setLastPasswordResetDate(Date.from(zdt.toInstant()));
        defaultUser.setEnabled(Boolean.TRUE);
        userRepository.save(defaultUser);
        
        alreadySetup = true;
    }
    
    @Transactional
    protected Authority createAuthorityIfNotFound(String name) {
    
        Authority authority = authorityRepository.findAuthorityByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }
    
    @Transactional
    protected Role createRoleIfNotFound(String name, List<Authority> authorities) {
        
        Role role = roleRepository.findRoleByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}