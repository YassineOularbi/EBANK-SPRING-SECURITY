package com.e_bank.exception;

public class BeneficiaryNotFoundException extends RuntimeException{
    public BeneficiaryNotFoundException(){
        super("beneficiary not found !");
    }
}
