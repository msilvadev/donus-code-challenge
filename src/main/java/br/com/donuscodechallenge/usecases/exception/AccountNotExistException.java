package br.com.donuscodechallenge.usecases.exception;

public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(){
    }

    public AccountNotExistException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountNotExistException(String message){
        super(message);
    }
}
