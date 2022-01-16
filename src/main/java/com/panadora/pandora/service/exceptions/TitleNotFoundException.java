package com.panadora.pandora.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Title Not Found")
public class TitleNotFoundException extends NotFoundException{

}
