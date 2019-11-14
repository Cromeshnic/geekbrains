package ru.dsi.geekbrains.testproject.homework7;

import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    //Сейчас роли не играет, но если конструкторы Car будут запускаться в разных потоках, то нужно обезопасить счётчик
    private static AtomicInteger CARS_COUNT;
    static {
        CARS_COUNT = new AtomicInteger(0);
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        //CARS_COUNT++;
        CARS_COUNT.incrementAndGet();
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
    }
}
