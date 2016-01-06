package com.rybak.effective.java.ch10;

/**
 * Не попадайте в зависимость от планировщика потоков
 *
 * Любая программа , чья корректность или производительность зависит от планировщика потоков,
 * скорее всего, переносимой не будет.
 *
 * Приоритетность потоков - одна из наименее переносимых особенностей на платформе Java.
 */
public class Item72
{
    //Ужасная реализация CountDownLatch - состояния активного ожидания не прирывается
    class ShowCountDownLatch
    {

        private int count;
        ShowCountDownLatch(int count)
        {
            if(count < 0 )
            {
                throw new IllegalArgumentException(count + " < 0");
            }
            this.count = count;
        }

        public void await()
        {
            while (true)
            {
                synchronized (this)
                {
                    if(count == 0) return;
                }
            }
        }

        public synchronized void countDown()
        {
            if(count != 0)
                count--;
        }
    }
}
