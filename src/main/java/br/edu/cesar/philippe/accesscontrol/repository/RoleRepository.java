package br.edu.cesar.philippe.accesscontrol.repository;

import br.edu.cesar.philippe.accesscontrol.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);

    List<Role> findRolesByNameIsIn(List<String> names);
}
