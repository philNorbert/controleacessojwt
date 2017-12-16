package br.edu.cesar.philippe.accesscontrol.repository;

import br.edu.cesar.philippe.accesscontrol.model.System;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<System, Long> {
    System findSystemByName(String name);
    
}
