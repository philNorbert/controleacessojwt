package br.edu.cesar.philippe.accesscontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UsernameAlreadyExists extends AuthenticationException {
    
    public UsernameAlreadyExists() {
        super("Usuário com esse login já existe!");
    }
    public UsernameAlreadyExists(String msg) {
        super(msg);
    }
    public UsernameAlreadyExists(String msg, Throwable t) {
        super(msg, t);
    }


}
