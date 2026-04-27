package org.fninfo.tg.exception;

public class MessageNotSentException extends RuntimeException {
    public MessageNotSentException(String message) {
        super(message);
    }
}
