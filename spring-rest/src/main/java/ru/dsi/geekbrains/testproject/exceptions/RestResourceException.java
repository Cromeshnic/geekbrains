package ru.dsi.geekbrains.testproject.exceptions;

public class RestResourceException extends RuntimeException {
    public RestResourceException(String message) {
        super(message);
    }
}
