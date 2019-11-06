package ru.dsi.geekbrains.testproject.exceptions;

public class MyArrayDataException extends MyException {
    private int rowNum;
    private int colNum;

    /**
     * @param rowNum erroneous row
     * @param colNum erroneous col
     */
    public MyArrayDataException(String message, int rowNum, int colNum) {
        super(message);
        this.rowNum =rowNum;
        this.colNum = colNum;
    }
}
