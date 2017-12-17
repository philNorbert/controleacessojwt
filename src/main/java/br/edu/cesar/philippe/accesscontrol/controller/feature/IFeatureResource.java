package br.edu.cesar.philippe.accesscontrol.controller.feature;

import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.request.FeatureDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

public interface IFeatureResource {
    List<Feature> findAll();

    ResponseEntity<String> delete(Long id);

    ResponseEntity<String> save(FeatureDTO featureDTO) throws URISyntaxException;

    ResponseEntity<String> update(Long id, FeatureDTO FeatureDTO) throws URISyntaxException;
}
