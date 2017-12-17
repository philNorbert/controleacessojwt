package br.edu.cesar.philippe.accesscontrol.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserPasswordDTO implements Serializable {

    private static final long serialVersionUID = 655745090000756997L;

    private String confirmPassword;
    private String newPassword;

}
