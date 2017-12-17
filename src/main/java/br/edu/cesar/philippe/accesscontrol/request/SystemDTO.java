package br.edu.cesar.philippe.accesscontrol.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SystemDTO implements Serializable {
    private static final long serialVersionUID = 3597759980314710942L;
    
    private String name;
    private List<FeatureDTO> features;
}
