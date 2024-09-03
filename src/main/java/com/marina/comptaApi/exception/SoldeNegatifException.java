package com.marina.comptaApi.exception;

public class SoldeNegatifException extends RuntimeException{
    public SoldeNegatifException(String message){
        super(message);
    }
}
