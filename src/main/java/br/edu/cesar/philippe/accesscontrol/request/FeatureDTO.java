package br.edu.cesar.philippe.accesscontrol.request;

import br.edu.cesar.philippe.accesscontrol.model.enums.FeatureType;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FeatureDTO implements Serializable {
    private static final long serialVersionUID = -5059273124472692138L;
    
    private String name;
    private FeatureType type;
}
