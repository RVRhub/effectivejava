package com.rybak.effective.java.ch6;

/**
 * Имитируйте расширяемые перечислимые типы с помощью интерфейсов
 *
 * Хотя вы не может написать расширяемый перечислимый тип, вы можете имитировать его
 * написав интерфейс с основным перечислимым типом , который реализует интерфейс.
 */
public class Item34
{
    public static void main(String[] args)
    {
        double x = 4;
        double y = 2;
        test(ExtendedOperation.class, x, y);
    }

    //Гарантирует , что объект Class представляет перечислимый тип и подтип принадлежащий к OperationInterface
    private static <T extends Enum<T> & OperationInterface> void test(Class<T> opSet, double x, double y)
    {
        for (OperationInterface op : opSet.getEnumConstants())
            System.out.printf("%f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
    }
}


interface OperationInterface
{
    double apply(double x, double y);
}
//Имитируйте расширяемые перечислимые типы с помощью интерфейсов
enum BasicOperation implements OperationInterface
{
    PLUS("+")
            {
                public double apply(double x, double y){ return x+y; }
            },
    MINUS("-")
            {
                public double apply(double x, double y){ return x-y; }
            },
    TIMES("*")
            {
                public double apply(double x, double y){ return x*y; }
            },
    DIVIDE("/")
            {
                public double apply(double x, double y){ return x/y; }
            };

    private final String symbol;
    BasicOperation(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public String toString()
    {
        return symbol;
    }
}

// Emulated extension enum
enum ExtendedOperation implements OperationInterface {
    EXP("^") {
        public double apply(double x, double y) {
            return Math.pow(x, y);
        }
    },
    REMAINDER("%") {
        public double apply(double x, double y) {
            return x % y;
        }
    };
    private final String symbol;
    ExtendedOperation(String symbol) {
        this.symbol = symbol;
    }
    @Override public String toString() {
        return symbol;
    }
}

