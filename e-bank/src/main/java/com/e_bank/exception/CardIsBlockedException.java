package com.e_bank.exception;

public class CardIsBlockedException extends RuntimeException{
    public CardIsBlockedException(){
        super("card blocked, please contact support !");
    }
}
