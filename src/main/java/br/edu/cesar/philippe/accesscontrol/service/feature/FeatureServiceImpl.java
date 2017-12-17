package br.edu.cesar.philippe.accesscontrol.service.feature;

import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.model.enums.FeatureType;
import br.edu.cesar.philippe.accesscontrol.repository.FeatureRepository;
import br.edu.cesar.philippe.accesscontrol.request.FeatureDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeatureServiceImpl implements IFeatureService {

    private FeatureRepository featureRepository;

    @Override
    public Feature findOne(Long id) throws ResourceNotFoundException {
        Feature feature = featureRepository.findOne(id);
        if (feature == null) {
            throw new ResourceNotFoundException(String.format("Função (%s) não encontrada!", id));
        }
        return feature;
    }

    @Override
    public List<Feature> findByType(FeatureType type) {
        return featureRepository.findFeaturesByFeatureType(type);
    }

    @Override
    public List<Feature> findAll() {
        return featureRepository.findAll();
    }

    @Override
    public Feature save(FeatureDTO featureDTO) throws ResourceAlreadyExists {
        String name = featureDTO.getName();
        Feature feature = featureRepository.findFeatureByName(name);
        if (feature != null) {
            throw new ResourceAlreadyExists(String.format("Função (%s) já foi cadastrada!", name));
        }
        return featureRepository.save(new Feature(name, featureDTO.getType()));
    }

    @Override
    public Feature update(Long id, FeatureDTO featureDTO) throws ResourceNotFoundException {
        Feature feature = featureRepository.findOne(id);
        if (feature == null) {
            throw new ResourceNotFoundException(String.format("Feature (%s) não encontrada!", id));
        }
        feature.setName(featureDTO.getName());
        feature.setFeatureType(featureDTO.getType());
        featureRepository.save(feature);
        return feature;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Feature feature = this.findOne(id);
        featureRepository.delete(feature);
    }

}
