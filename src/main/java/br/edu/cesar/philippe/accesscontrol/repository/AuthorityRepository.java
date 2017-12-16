package br.edu.cesar.philippe.accesscontrol.repository;

import br.edu.cesar.philippe.accesscontrol.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findAuthorityByName(String name);
    List<Authority> findAuthoritiesByNameIsIn(List<String> names);
}
