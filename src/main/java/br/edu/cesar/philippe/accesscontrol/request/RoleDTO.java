package br.edu.cesar.philippe.accesscontrol.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 7102122678559656740L;
    
    private String name;
    private List<AuthorityDTO> authorizations;
    
}