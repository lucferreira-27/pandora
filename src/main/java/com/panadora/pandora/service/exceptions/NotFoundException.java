package com.panadora.pandora.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class NotFoundException extends RuntimeException {
    NotFoundException() {
        super("Resource Not Found");
    }

    NotFoundException(String msg) {
        super(msg);

    }
}
