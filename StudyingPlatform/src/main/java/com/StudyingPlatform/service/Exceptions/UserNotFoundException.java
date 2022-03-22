package com.StudyingPlatform.service.Exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){

    }
    public UserNotFoundException(String m){
        super(m);
    }
}
