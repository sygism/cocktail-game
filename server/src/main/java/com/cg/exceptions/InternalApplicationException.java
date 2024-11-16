package com.cg.exceptions;

import static java.lang.String.format;

public class InternalApplicationException extends RuntimeException {
    public InternalApplicationException(String message) {
        super(format("Internal Error: %s", message));
    }
}
