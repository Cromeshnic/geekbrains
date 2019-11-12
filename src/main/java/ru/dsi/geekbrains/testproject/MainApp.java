package ru.dsi.geekbrains.testproject;

import ru.dsi.geekbrains.testproject.exceptions.MyException;
import ru.dsi.geekbrains.testproject.homework6.PhoneDictionary;

import java.util.*;

public class MainApp {

    public static void main(String[] args) {
        /*Task t1 = new Task(1,"test1", "Me");
        Task t2 = new Task(2, "test2", "Myself");
        TaskService tt = new TaskService();
        tt.addTask(t1);
        tt.addTask(t2);
        tt.addTask(t2);
        tt.addTask(null);
        tt.removeTask(2);
        tt.printTasks();*/
        homework6_1();
        homework6_2();
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


