package ru.dsi.geekbrains.testproject.homework7;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

//Можно реализовать в Road или в Race, но архитектурно правильнее отдельным этапом.
public class StartStage extends Stage {
    //cb и latch можно не объявлять волатильными, т.к. в параллельном выполнении не будет присваивания, только при предварительной инициализации в main
    private CyclicBarrier cb;
    private CountDownLatch latch;
    public StartStage(int participants, CountDownLatch countDownLatch) {
        this.length = 0;
        this.description = "Начало гонки";
        this.latch = countDownLatch;
        this.cb = new CyclicBarrier(participants);
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " готовится : " + description);
            Thread.sleep(500);
            System.out.println(c.getName() + " готов : " + description);
            if(latch!=null) {
                latch.countDown();
            }
            cb.await();
            System.out.println(c.getName() + " стартовал : " + description);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
