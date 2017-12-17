package br.edu.cesar.philippe.accesscontrol.service.system;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.System;
import br.edu.cesar.philippe.accesscontrol.request.SystemDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public interface ISystemService {
    System findOne(Long id) throws ResourceNotFoundException;

    List<System> findAll();

    System save(SystemDTO systemDTO) throws ResourceAlreadyExists;

    System update(Long id, SystemDTO systemDTO) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
