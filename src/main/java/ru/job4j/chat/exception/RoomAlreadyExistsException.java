package ru.job4j.chat.exception;

public class RoomAlreadyExistsException extends RuntimeException {

    public RoomAlreadyExistsException(String message) {
        super(message);
    }
}
