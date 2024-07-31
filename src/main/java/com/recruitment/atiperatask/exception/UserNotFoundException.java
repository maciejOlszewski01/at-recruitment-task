package com.recruitment.atiperatask.exception;

import com.recruitment.atiperatask.utility.ErrorMessages;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND);
    }
}
