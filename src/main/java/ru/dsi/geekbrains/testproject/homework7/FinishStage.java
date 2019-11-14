package ru.dsi.geekbrains.testproject.homework7;

import java.util.concurrent.CountDownLatch;

//Можно реализовать в Road или в Race, но архитектурно правильнее отдельным этапом.
public class FinishStage extends Stage {

    private volatile Car winner;//присваивание/считывание будет исполняться в параллельных потоках
    private CountDownLatch latch;

    public FinishStage(int participants, CountDownLatch countDownLatch) {
        this.winner = null;
        this.length = 0;
        this.description = "Конец гонки";
        this.latch = countDownLatch;
    }

    @Override
    public void go(Car c) {
        if(this.winner==null){//чтобы каждый раз не заходить в synchronized, когда победитель уже определён
            synchronized (this){
                if(this.winner==null){
                    this.winner = c;
                }
            }
        }
        System.out.println(c.getName() + " финишировал : " + description);
        if(this.winner==c){//проверка по ссылке уместна
            System.out.println(c.getName() + " победил : " + description);
        }
        if(latch!=null) {
            latch.countDown();
        }
    }

    public Car getWinner() {
        return winner;
    }
}
