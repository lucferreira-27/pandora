package com.panadora.pandora.service.exceptions;

import com.panadora.pandora.service.AddImage;

public class AddImageException extends Exception{
    public AddImageException(){

    }
    public AddImageException(String msg){
        super(msg);
    }
}
