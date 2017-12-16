package br.edu.cesar.philippe.accesscontrol.security;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtAuthResponse implements Serializable {
    private static final long serialVersionUID = 922628450581588997L;
    private final String token;
}
