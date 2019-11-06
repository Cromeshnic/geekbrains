package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyArrayDataException;
import ru.dsi.geekbrains.testproject.exceptions.MyArraySizeException;
import ru.dsi.geekbrains.testproject.exceptions.MyException;

public class MainApp {

    final static int ARRAY_SIZE = 4;

    public static void main(String[] args) {
        Task t1 = new Task(1,"test1", "Me");
        Task t2 = new Task(2, "test2", "Myself");
        TaskService tt = new TaskService();
        tt.addTask(t1);
        tt.addTask(t2);
        tt.addTask(t2);

        String arr[][] = new String[3][4];
        try {
            arraySum(arr);
        } catch (MyException e) {
            e.printStackTrace();
        }

        arr = new String[ARRAY_SIZE][3];
        try {
            arraySum(arr);
        } catch (MyException e) {
            e.printStackTrace();
        }

        arr = new String[ARRAY_SIZE][ARRAY_SIZE];
        try {
            arraySum(arr);
        } catch (MyException e) {
            e.printStackTrace();
        }

        int v;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            for (int j = 0; j < ARRAY_SIZE; j++) {
                v = (i+j % 3);
                arr[i][j]=String.valueOf(v);
                System.out.println("arr["+i+"]["+j+"] = "+arr[i][j]);
            }
        }
        try {
            System.out.println(arraySum(arr));
        } catch (MyException e) {
            e.printStackTrace();
        }
        arr[1][1] = "weird";
        try {
            System.out.println(arraySum(arr));
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    //#4
    public static long arraySum(String[][] arr) throws MyException {
        if(arr.length!=ARRAY_SIZE){
            throw new MyArraySizeException(ARRAY_SIZE, ARRAY_SIZE, -1, arr.length);
        }
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].length!=ARRAY_SIZE){
                throw new MyArraySizeException(ARRAY_SIZE, ARRAY_SIZE, i, arr[i].length);
            }
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                }catch (NumberFormatException e){
                    throw new MyArrayDataException("NumberFormatException at ["+i+"]["+j+"] :"+arr[i][j],i,j);
                }
            }
        }
        return sum;
    }
}
