package com.rybak.effective.java.ch7;

import java.math.BigInteger;

/**
 * Проверяйте достоверность параметров
 */
public class Item38
{
    public static void main(String[] args) {

    }

    /**
     * Возвращает объект BigInteger, значение которого является модуль данного числа по основаниию m.
     * Этот метод отличается от remainder тем, что всегда возвращет не отрицательно число BigInteger.
     *
     * @param m - модуль, должен быть положительным число
     * @return this mod m
     * @throws ArithmeticException, if m < 0
     */
    public BigInteger mod(BigInteger m)
    {
        if(m.signum() <= 0 )
            throw new ArithmeticException("Modulus not positive");

        //...

        return BigInteger.ONE;
    }

    //Закрытая вспомогательная функция для рекурсивной сортировки
    //используйте утверждение assertion
    private static void sort(long a[], int offset, int length)
    {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length - offset;

        //
    }
}
