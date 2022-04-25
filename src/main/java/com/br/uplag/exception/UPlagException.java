package com.br.uplag.exception;

public class UPlagException extends Exception {
    public UPlagException() {
    }

    public UPlagException(String message) {
        super(message);
    }

    public UPlagException(Throwable cause) {
        super(cause);
    }
}
