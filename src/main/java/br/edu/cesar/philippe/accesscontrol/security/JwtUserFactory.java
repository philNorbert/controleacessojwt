package br.edu.cesar.philippe.accesscontrol.security;

import br.edu.cesar.philippe.accesscontrol.model.Authority;
import br.edu.cesar.philippe.accesscontrol.model.Role;
import br.edu.cesar.philippe.accesscontrol.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> returnList = new ArrayList<>();
        roles.stream()
                .map(role -> {
                    List<Authority> authorities = role.getAuthorities();
                    return authorities.stream()
                            .map(Authority::getName)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    }
                ).forEach(returnList::addAll);
        return returnList;
    }

}
