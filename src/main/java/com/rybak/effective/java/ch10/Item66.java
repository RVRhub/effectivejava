package com.rybak.effective.java.ch10;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Синхронизируйте доступ потоков к совместно используемым изменяемым данным
 *
 * Чтение и запись отдельной переменой ( если это не переменная типа long или double ) являются атомарными операциями .
 *
 * Возможно вы слышали , что для повышения производительности при чтении и записи атомарных данных нужно избегать синхронизации.
 * Это не правильный совет с опасными последствиями.
 * Хотя свойство атомарности гарантирует, что при чтении атомарных данных поток не увидит случайного значения, в то же время нет гарантии ,
 * что значение, записанное одним потоком , будет увидено другим : синхронизация необходима как для блокирования потока,
 * так и для надежного взаимодействия между ними.
 * Это является следствием сугубо технического аспекта языка программирования Java, который называется моделью памяти (JLS17).
 *
 * Когда несколько потоков совместно работают с изменяемыми данными , каждый поток, который читает или запbсывает эти данные ,
 * должен пользоваться блокировкой .
 *
 */
public class Item66
{
    //как вариант объявить данное поле volatile (непостоянным) , он гарантирует , что любой поток , который прочитает поле  ,
    //увидит недавно записанное значение
    private static boolean stopRequested;

    //правильно синхронизированное совместное завершения потока
    private static synchronized void requestStop()
    {
        stopRequested = true;
    }

    private static synchronized boolean stopRequested()
    {
        return stopRequested;
    }
//todo от синхронизации нет пользы , если операции чтения и записи не синхронизированы.
    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(
                new Runnable() {
                    public void run() {
                        int i = 0;

//                        if(!stopRequested)
//                            while (!stopRequested)
//                                i++;
                        //HotSpot оптимизировал  след строчки
                       // while (!stopRequested)
                       //     i++;

                        while (!stopRequested())
                            i++;
                    }
                }
        );
        backgroundThread.start();
        TimeUnit.SECONDS.sleep(1);
        requestStop();

    }

    //Ошибка - требуется синхронизация
    private static volatile int nextSerialNumber = 0;
    public static int getNextSerialNumber()
    {
        return nextSerialNumber++; // не атомарная операциия
    }

    //лучший способ
    private static final AtomicLong nextSerialNum = new AtomicLong();
    public static long getNextSerialNum()
    {
        return nextSerialNum.getAndIncrement();
    }
}
