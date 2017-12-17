package br.edu.cesar.philippe.accesscontrol.repository;

import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.model.enums.FeatureType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findFeaturesByFeatureType(FeatureType feature);

    List<Feature> findFeaturesByNameIsIn(List<String> featureNames);
    Feature findFeatureByName(String name);
    
}
