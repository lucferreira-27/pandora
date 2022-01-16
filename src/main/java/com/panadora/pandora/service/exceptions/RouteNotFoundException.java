package com.panadora.pandora.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Route Not Found")
public class RouteNotFoundException extends NotFoundException{
    public RouteNotFoundException(){
        super("Route Not Found");
    }
}
