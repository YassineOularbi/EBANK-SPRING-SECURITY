package com.e_bank.exception;

public class CardDeactivatedException extends RuntimeException{
    public CardDeactivatedException(){
        super("card deactivated, please activate your card");
    }
}
