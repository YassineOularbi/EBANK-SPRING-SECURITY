package com.e_bank.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("user not found !");
    }
}
