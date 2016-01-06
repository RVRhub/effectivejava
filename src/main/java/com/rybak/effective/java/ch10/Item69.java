package com.rybak.effective.java.ch10;

import java.util.concurrent.*;

/**
 * Предпочитайте использовать утилиты параллельности, нежели wait и notify
 *
 * Зная о трудности использования wait и notify, следует испльзовать высокоуровневые утилиты параллельности.
 * - Структура экзекуторов
 * - Параллельные коллекции
 * - Синхронизаторы
 *
 * Невозможно исключить параллельную деятельность из параллельной коллекции , ее блокировка будет иметь действие ,
 * а только затормозит выполнение программы.
 *
 * BlockingQueue - блокирующие очереди для рабочих очередей
 *
 * Синхронизаторы - объекты которые дают ждать потокам друг друга , позволяя им координировать их деятельность .
 * CountDownLatch or Semaphore
 *
 * wait всегда запускается в цикле while !!!
 */
public class Item69
{

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new Runnable() {
//            public void run() {
//                System.out.println("Test");
//            }
//        });
        System.out.println(time(executorService, 1));
        executorService.shutdown();
    }

    private static final ConcurrentMap<String,String> map = new ConcurrentHashMap<String, String>();

    //Параллельная традиционная схема поверх ConcurrentMap - не оптимальная
    public static String intern(String s)
    {
        return map.putIfAbsent(s,s);
    }

    //Параллельная традиционная схема поверх ConcurrentMap - работает быстрее
    public static String intern(String key, String value)
    {
        String result = map.get(key);
        if(result == null)
        {
            result = map.putIfAbsent(key, value);
            if(result == null)
                result = value;
        }

        return result;
    }

    public static long time (Executor executor, int concurrency ) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);

        for(int i = 0; i < concurrency; i++)
        {
            executor.execute(new Runnable() {
                public void run() {
                    ready.countDown(); // Tell timer we're ready - защелка
                    try
                    {
                        start.await();//will till peers are ready
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                    }
                    finally {
                        done.countDown();//Tell timer we're done
                    }
                }
            });
        }

        ready.await(); //wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown();//and they're off
        done.await();

        return System.nanoTime() - startNanos;

    }

}
