package com.panadora.pandora.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Item Not Found")
public class ItemNotFoundException extends NotFoundException {
}
