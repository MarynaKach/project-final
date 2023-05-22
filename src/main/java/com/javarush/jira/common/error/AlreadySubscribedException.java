package com.javarush.jira.common.error;

public class AlreadySubscribedException extends AppException{
    public AlreadySubscribedException(String message) {
        super(message);
    }
}
