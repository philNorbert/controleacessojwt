package br.edu.cesar.philippe.accesscontrol.service.feature;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.model.enums.FeatureType;
import br.edu.cesar.philippe.accesscontrol.request.FeatureDTO;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public interface IFeatureService {
    Feature findOne(Long id) throws ResourceNotFoundException;

    List<Feature> findByType(FeatureType type);

    List<Feature> findAll();

    Feature save(FeatureDTO request) throws ResourceAlreadyExists;

    Feature update(Long id, FeatureDTO request) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
