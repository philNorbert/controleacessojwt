package br.edu.cesar.philippe.contoleacesso.security.repository;

import br.edu.cesar.philippe.contoleacesso.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
