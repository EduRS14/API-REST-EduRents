package com.ingsoft.tf.api_edurents.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super("El rol no existe");
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}

