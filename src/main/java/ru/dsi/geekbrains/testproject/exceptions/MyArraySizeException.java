package ru.dsi.geekbrains.testproject.exceptions;

public class MyArraySizeException extends MyException {
    private int requiredRows;
    private int requredCols;
    private int errorRowNum;
    private int errorSize;
    /**
     *
     * @param requiredRows
     * @param requiredCols
     * @param errorRowNum -1 if actual row number != requiredRows, incorrect sized row number otherwise
     * @param errorSize incorrect row number (with errorRowNum=-1) or incorrect row[errorRowNum] size otherwise
     */
    public MyArraySizeException(int requiredRows, int requiredCols, int errorRowNum, int errorSize) {
        super("Incorrect array size. Required: ["+requiredRows+","+requiredCols+"]. Actual "+(errorRowNum==-1 ? "rows "+errorSize: "cols at row["+errorRowNum+"] = "+errorSize));
        this.requiredRows = requiredRows;
        this.requredCols = requiredCols;
        this.errorRowNum = errorRowNum;
        this.errorSize = errorSize;
    }

    public int getRequiredRows() {
        return requiredRows;
    }

    public int getRequredCols() {
        return requredCols;
    }

    public int getErrorRowNum() {
        return errorRowNum;
    }

    public int getErrorSize() {
        return errorSize;
    }
}
