package com.StudyingPlatform.service.Exceptions;

public class EmptyResultSetException extends Exception{
    public EmptyResultSetException(){

    }
    public EmptyResultSetException(String m){
        super(m);
    }
}
