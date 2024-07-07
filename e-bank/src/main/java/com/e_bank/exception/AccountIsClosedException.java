package com.e_bank.exception;

public class AccountIsClosedException extends RuntimeException{
    public AccountIsClosedException(){
        super("account is already closed, please contact support !");
    }
}
