package br.edu.cesar.philippe.accesscontrol.controller.feature;

import br.edu.cesar.philippe.accesscontrol.ApiVersion;
import br.edu.cesar.philippe.accesscontrol.exception.ResourceAlreadyExists;
import br.edu.cesar.philippe.accesscontrol.model.Feature;
import br.edu.cesar.philippe.accesscontrol.request.FeatureDTO;
import br.edu.cesar.philippe.accesscontrol.service.feature.IFeatureService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = ApiVersion.V1 + "/feature", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureResource implements IFeatureResource {

    private IFeatureService featureService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public List<Feature> findAll() {
        return featureService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Feature> findFeature(@PathVariable Long id) {
        Feature feature;
        try {
            feature = featureService.findOne(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(feature);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            featureService.delete(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> save(@RequestBody FeatureDTO featureDTO) throws
            URISyntaxException {
        Feature feature;
        try {
            feature = featureService.save(featureDTO);
        } catch (ResourceAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/feature/" + feature.getId())).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_AUTHORITY')")
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody FeatureDTO FeatureDTO) throws
            URISyntaxException {
        Feature feature;
        try {
            feature = featureService.update(id, FeatureDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

        return ResponseEntity.created(new URI(ApiVersion.V1 + "/feature/" + feature.getId())).build();
    }

}