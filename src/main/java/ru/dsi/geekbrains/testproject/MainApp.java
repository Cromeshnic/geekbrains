package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.homework5.Apple;
import ru.dsi.geekbrains.testproject.homework5.Box;

public class MainApp {

    public static void main(String[] args) {
        Task t1 = new Task(1,"test1", "Me");
        Task t2 = new Task(2, "test2", "Myself");
        TaskService tt = new TaskService();
        tt.addTask(t1);
        tt.addTask(t2);
        tt.addTask(t2);
        tt.addTask(null);
        tt.removeTask(2);
        tt.printTasks();

        Box<Apple> appleBox1 = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        //Box<Orange> orangeBox = new Box<>();
        //orangeBox.addAll(appleBox1);//doesn't work
        appleBox1.add(new Apple());
        appleBox1.add(new Apple());
        System.out.println("box #1:"+appleBox1.getWeight());
        appleBox2.add(new Apple());
        System.out.println("box #2:"+appleBox2.getWeight());
        appleBox1.pourAllTo(appleBox2);
        System.out.println("box #1:"+appleBox1.getWeight());
        System.out.println("box #2:"+appleBox2.getWeight());
    }

}

/** stdout:
 *
 * Tasks:
 * Task #1 [test1]
 * null
 * ---
 * box #1:2.0
 * box #2:1.0
 * box #1:0.0
 * box #2:3.0
 */

