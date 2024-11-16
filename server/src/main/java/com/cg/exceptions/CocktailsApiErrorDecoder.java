package com.cg.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CocktailsApiErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new ExternalApiException("received a bad request");
            case 401 -> new ExternalApiException("unauthorized");
            case 404 -> new ExternalApiException("requested resource not found");
            case 500 -> new ExternalApiException("internal server error");
            default -> new ExternalApiException("unknown response status " + response.status());
        };
    }
}
