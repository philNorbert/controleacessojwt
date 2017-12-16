package br.edu.cesar.philippe.accesscontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends Exception {
    
    public ResourceAlreadyExists() {
        super("Resource já existente!");
    }
    public ResourceAlreadyExists(String msg) {
        super(msg);
    }
    public ResourceAlreadyExists(String msg, Throwable t) {
        super(msg, t);
    }


}
