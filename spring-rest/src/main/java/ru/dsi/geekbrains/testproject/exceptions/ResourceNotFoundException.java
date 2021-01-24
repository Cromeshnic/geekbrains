package ru.dsi.geekbrains.testproject.exceptions;

public class ResourceNotFoundException extends RestResourceException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}