package com.cg.exceptions;

import static java.lang.String.format;

public class ExternalApiException extends RuntimeException {
    public ExternalApiException(String message) {
        super(format("TheCocktailsDB API: %s", message));
    }
}
