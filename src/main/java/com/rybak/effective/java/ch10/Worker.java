package com.rybak.effective.java.ch10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Закрытая блокировка
 */
public class Worker
{
    private Random random = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();


    //public synchronized void stageOne() { synchronized (this) { }
    public void stageOne() {
        System.out.println("stageOne1: " + Thread.currentThread().getId());
        synchronized (list1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(random.nextInt(100));
        }



    }

   // public synchronized void stageTwo() {
   public void stageTwo() {
       System.out.println("stageOne2:  " + Thread.currentThread().getId());
       synchronized (list2) {
           try {
               Thread.sleep(1);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

           list2.add(random.nextInt(100));
       }

   }

    public void process()
    {
        for(int i = 0; i < 1000; i++)
        {
            stageOne();
            stageTwo();
        }
    }

    public static void main(String[] args)
    {
        Worker worker = new Worker();
        worker.start();
    }

    public void start()
    {
        System.out.println("Starting ...");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                process();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                process();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long end = System.currentTimeMillis();
        System.out.println("Time take: " + (end-start));
        System.out.println("List1: " + list1.size() + "List2: " + list2.size());
    }
}
