package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;
import ru.dsi.geekbrains.testproject.homework6.PhoneDictionary;
import ru.dsi.geekbrains.testproject.homework7.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class MainApp {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        homework10();
    }

    public static void homework10(){
        TaskService taskService = null;
        try {
            taskService = new TaskService();
        } catch (MyException e) {
            e.printStackTrace();
        }
        taskService.addTask(new Task(1,"first", "Alice","open"));
        taskService.addTask(new Task(2,"second", "Bob","open"));
        taskService.addTask(new Task(3,"third", "Alice","in progress"));
        taskService.addTask(new Task(4,"weird", "Alice",null));
        taskService.addTask(new Task(5,"old", "Clark","closed"));
        taskService.addTask(new Task(6,"oldest", "Bob","closed"));

        taskService.removeTask(2);
        System.out.println(taskService.taskExists(2));
        System.out.println(taskService.taskExists(5));
        taskService.removeTask("third");

        taskService.printTasks();
    }

    public static void homework9(){
        TaskService taskService = null;
        try {
            taskService = new TaskService();
        } catch (MyException e) {
            e.printStackTrace();
        }
        taskService.addTask(new Task(1,"first", "Alice","open"));
        taskService.addTask(new Task(2,"second", "Bob","open"));
        taskService.addTask(new Task(3,"third", "Alice","in progress"));
        taskService.addTask(new Task(4,"weird", "Alice",null));
        taskService.addTask(new Task(5,"old", "Clark","closed"));
        taskService.addTask(new Task(6,"oldest", "Bob","closed"));

        try {
            File f = new File("tasks");
            taskService.exportToFile(taskService.getTasksSortedByStatus(), f);
            for (Task task : taskService.loadFromFile(f)) {
                System.out.println(task);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void homework8(){
        TaskService taskService = null;
        try {
            taskService = new TaskService();
        } catch (MyException e) {
            e.printStackTrace();
        }
        taskService.addTask(new Task(1,"first", "Alice","open"));
        taskService.addTask(new Task(2,"second", "Bob","open"));
        taskService.addTask(new Task(3,"third", "Alice","in progress"));
        taskService.addTask(new Task(4,"weird", "Alice",null));
        taskService.addTask(new Task(5,"old", "Clark","closed"));
        taskService.addTask(new Task(6,"oldest", "Bob","closed"));
        //a.
        taskService.getTasksByStatus("open").forEach(System.out::println);
        //b.
        System.out.println(taskService.taskExists(4));
        System.out.println(taskService.taskExists(10));
        //c.
        taskService.getTasksSortedByStatus().forEach(System.out::println);
        //d.
        System.out.println(taskService.getTaskCountByStatus("closed"));
        System.out.println(taskService.getTaskCountByStatus(null));
    }

    public static void homework7(){
        CountDownLatch startCountdown = new CountDownLatch(CARS_COUNT);
        CountDownLatch finishCountdown = new CountDownLatch(CARS_COUNT);
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        FinishStage finish = new FinishStage(CARS_COUNT, finishCountdown);
        Race race = new Race(new StartStage(CARS_COUNT, startCountdown), new Road(60), new Tunnel(CARS_COUNT/2), new Road(40), finish);
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }

        try {
            startCountdown.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            finishCountdown.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Поздравление победителя : "+finish.getWinner().getName());

    }

    public static void homework6_1(){
        String text = "У попа была собака,\n" +
                "Он ее любил.\n" +
                "Она съела кусок мяса,\n" +
                "Он ее убил!\n" +
                "В землю закопал,\n" +
                "Надпись написал, что:\n" +
                "\"У попа была собака,\n" +
                "Он ее любил,\n" +
                "Она съела кусок мяса,\n" +
                "Он ее убил\".";
        List<String> wordsAll = Arrays.asList(text.toLowerCase().split("[\\s,\\.\\n!\"]+"));
        Set<String> wordsUnique = new HashSet<>();
        //Считаем число вхождений каждого слова
        System.out.println(" --- 1.1 ---");
        //Вариант 1: Мало кода, но лишние циклы по wordsAll (во frequency)
        wordsUnique.addAll(wordsAll);
        System.out.println(wordsAll.size());
        System.out.println(wordsUnique.size());
        wordsUnique.forEach(s -> System.out.println(s+" : "+Collections.frequency(wordsAll, s)));

        System.out.println(" --- 1.2 ---");
        //Вариант 2: считаем количество вхождений сами: меньше циклов, но дополнительный Map со счётчиками
        wordsUnique.clear();
        Map<String, Integer> counters = new HashMap<>();
        for (String s : wordsAll) {
            counters.compute(s, (k, cnt) -> cnt==null? 1: cnt+1);
            wordsUnique.add(s);
        }
        System.out.println(wordsAll.size());
        System.out.println(wordsUnique.size());
        counters.forEach((s, count) -> System.out.println(s+" : "+count));
    }

    public static void homework6_2(){
        PhoneDictionary dictionary = new PhoneDictionary();
        try {
            dictionary.add("Alice","123456789");
            dictionary.add("Bob","99999999");
            dictionary.add("Bob","111111");
            dictionary.add("Carl","+111111");
        } catch (MyException e) {
            e.printStackTrace();
        }
        Arrays.asList(new String[]{"Alice", "Bob", "Carl"}).forEach(
                name ->
                System.out.println(name+" : "+String.join(",", dictionary.get(name))));
    }

}

/* stdout:
 *  --- 1.1 ---
 * 34
 * 18
 * ее : 4
 * она : 2
 * у : 2
 * любил : 2
 * написал : 1
 * надпись : 1
 * попа : 2
 * землю : 1
 * убил : 2
 * закопал : 1
 * в : 1
 * съела : 2
 * что: : 1
 * была : 2
 * кусок : 2
 * собака : 2
 * он : 4
 * мяса : 2
 *  --- 1.2 ---
 * 34
 * 18
 * ее : 4
 * она : 2
 * любил : 2
 * у : 2
 * написал : 1
 * надпись : 1
 * землю : 1
 * попа : 2
 * закопал : 1
 * убил : 2
 * в : 1
 * съела : 2
 * что: : 1
 * была : 2
 * кусок : 2
 * мяса : 2
 * он : 4
 * собака : 2
 * ru.dsi.geekbrains.testproject.exceptions.MyException: Wrong number format: number = '+111111', format = '^\d*$'
 * 	at ru.dsi.geekbrains.testproject.homework6.PhoneDictionary.add(PhoneDictionary.java:23)
 * 	at ru.dsi.geekbrains.testproject.MainApp.homework6_2(MainApp.java:64)
 * 	at ru.dsi.geekbrains.testproject.MainApp.main(MainApp.java:21)
 * Alice : 123456789
 * Bob : 111111,99999999
 * Carl :
 */


