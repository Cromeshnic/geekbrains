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

/**
 * stdout:
 * ru.dsi.geekbrains.testproject.exceptions.MyException: Task 'Task #2 [test2]' already exists in repository
 * ru.dsi.geekbrains.testproject.exceptions.MyArraySizeException: Incorrect array size. Required: [4,4]. Actual rows 3
 * 	at ru.dsi.geekbrains.testproject.MainApp.arraySum(MainApp.java:64)
 * 	at ru.dsi.geekbrains.testproject.MainApp.main(MainApp.java:21)
 * ru.dsi.geekbrains.testproject.exceptions.MyArraySizeException: Incorrect array size. Required: [4,4]. Actual cols at row[0] = 3
 * 	at ru.dsi.geekbrains.testproject.MainApp.arraySum(MainApp.java:69)
 * 	at ru.dsi.geekbrains.testproject.MainApp.main(MainApp.java:28)
 * ru.dsi.geekbrains.testproject.exceptions.MyArrayDataException: NumberFormatException at [0][0] :null
 * 	at ru.dsi.geekbrains.testproject.MainApp.arraySum(MainApp.java:75)
 * 	at ru.dsi.geekbrains.testproject.MainApp.main(MainApp.java:35)
 * arr[0][0] = 0
 * arr[0][1] = 1
 * arr[0][2] = 2
 * arr[0][3] = 0
 * arr[1][0] = 1
 * arr[1][1] = 2
 * arr[1][2] = 3
 * arr[1][3] = 1
 * arr[2][0] = 2
 * arr[2][1] = 3
 * arr[2][2] = 4
 * arr[2][3] = 2
 * arr[3][0] = 3
 * arr[3][1] = 4
 * arr[3][2] = 5
 * arr[3][3] = 3
 * ru.dsi.geekbrains.testproject.exceptions.MyArrayDataException: NumberFormatException at [1][1] :weird
 * 36
 * 	at ru.dsi.geekbrains.testproject.MainApp.arraySum(MainApp.java:75)
 * 	at ru.dsi.geekbrains.testproject.MainApp.main(MainApp.java:55)
 *
 * Process finished with exit code 0
 */
