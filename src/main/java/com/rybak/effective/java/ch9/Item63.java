package com.rybak.effective.java.ch9;

/**
 * В описание исключения добавляйте информацию о себе
 *
 *
 */
public class Item63
{
    public class IndexOutOfBoundsException extends RuntimeException
    {
        private final int lowerBound;
        private final int upperBound;
        private final int index;

        /**
         * Констатируем IndexOutOfBoundsException
         *
         * @param lowerBound - самое меньшее из разрешенных значений индекса
         * @param upperBound - самое большое из разрешенных значений индекса
         * @param index - действительное значение индекса
         */
        public IndexOutOfBoundsException(int lowerBound, int upperBound, int index)
        {
            //Генерируем описание исключения,
            //фиксирующее обстоятельства отказа
            super("Lower Bound: " + lowerBound +
                  ", Upper bound: " + upperBound +
                  ", Index: " + index);

            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.index = index;


        }
    }
}
