package org.fninfo.tg.exception;

public class RedisException extends RuntimeException {
    public RedisException(String message) {
        super(message);
    }
}
