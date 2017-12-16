package br.edu.cesar.philippe.accesscontrol.security;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtAuthRequest implements Serializable {
    private static final long serialVersionUID = 805745097074756477L;
    private String username;
    private String password;
}
