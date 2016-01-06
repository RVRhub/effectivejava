package com.rybak.effective.java.ch10;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Предпочитайте использовать экзекутеров и задания вместо потоков
 *
 * Ключевым понятием теперь является рабочая единица , которая называется задачей.
 * Два вида задачи : Runnable , Callable
 * Общий механизм называет службой экзекьютеров
 */
public class Item68 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            public void run() {

            }
        });

        executorService.shutdown();

        //Пишете небольшую программу или легко нагруженный сервер
        ExecutorService executorServicePool = Executors.newCachedThreadPool();
        ExecutorService executorServiceFixedPool = Executors.newFixedThreadPool(10);

    }
}
