package com.StudyingPlatform.service.Exceptions;

public class MessageNotFoundException extends Exception{
    public MessageNotFoundException(){

    }
    public MessageNotFoundException(String m){
        super(m);
    }
}
