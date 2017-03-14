package com.user.exception;

import lombok.Data;

@Data
public class UserException extends RuntimeException{

    public UserException(String errorMessage) {
        super(errorMessage);
    }
}
