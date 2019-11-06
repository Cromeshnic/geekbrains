package ru.dsi.geekbrains.testproject.exceptions;

public class MyRepositorySizeException extends MyException {
    private int actualSize;

    public MyRepositorySizeException(int actualSize) {
        super("No space left in repository, max size = "+actualSize);
        this.actualSize = actualSize;
    }

    public int getActualSize() {
        return actualSize;
    }
}
