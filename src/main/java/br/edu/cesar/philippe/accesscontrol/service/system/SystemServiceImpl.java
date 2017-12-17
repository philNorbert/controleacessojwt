package br.edu.cesar.philippe.accesscontrol.service.system;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.model.System;
import br.edu.cesar.philippe.accesscontrol.repository.FeatureRepository;
import br.edu.cesar.philippe.accesscontrol.repository.SystemRepository;
import br.edu.cesar.philippe.accesscontrol.request.FeatureDTO;
import br.edu.cesar.philippe.accesscontrol.request.SystemDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SystemServiceImpl implements ISystemService {

    private SystemRepository systemRepository;
    private FeatureRepository featureRepository;

    @Override
    public System findOne(Long id) throws ResourceNotFoundException {
        System system = systemRepository.findOne(id);
        if (system == null) {
            throw new ResourceNotFoundException(String.format("Sistema (%s) não encontrado!", id));
        }
        return system;
    }

    @Override
    public List<System> findAll() {
        return systemRepository.findAll();
    }

    @Override
    public System save(SystemDTO systemDTO) throws ResourceAlreadyExists {
        String name = systemDTO.getName();
        System system = systemRepository.findSystemByName(name);
        if (system != null) {
            throw new ResourceAlreadyExists(String.format("Sistema (%s) já foi cadastrado!", name));
        }
        return systemRepository.save(new System(name));
    }

    @Override
    public System update(Long id, SystemDTO systemDTO) throws ResourceNotFoundException {
        List<String> systemNames = systemDTO.getFeatures().stream()
                .map(FeatureDTO::getName)
                .collect(Collectors.toList());
        List<Feature> features = featureRepository.findFeaturesByNameIsIn(systemNames);

        System system = systemRepository.findOne(id);
        if (system == null) {
            throw new ResourceNotFoundException(String.format("Sistema (%s) não encontrado!", id));
        }

        system.setName(systemDTO.getName());
        system.setFeatures(features);
        systemRepository.save(system);
        return system;
    }


    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        System system = this.findOne(id);
        systemRepository.delete(system);
    }


}
