package com.rybak.effective.java.ch6;

import java.util.HashMap;
import java.util.Map;

/**
 * Используйте перечисленные типы вместо констант int
 *
 * Когда вам требуется фиксированный набор констант ?????????
 * Стратегия перечислимых шаблонов , если несколько констант имеют одинаковые реакции
 *
 * Перечислимые типы в java - являются классами , которые экспортируют один экземпляр каждой перечислимой константы ,
 * испльзую открытое статичексое завершоное поле. ???????
 *
 * До java 1.5 перечислимые типы были сделаны через int
 * пример: Вкусный цитрусовый приправлен яблочным соусом
 * int i = (APPLE_FUJI - ORANGE_TEMPLE) / APPLE_PIPPIN;
 *
 *
 * Перечислимые типы в Java являются классами, которые экспортируют один экземпляр каждой перечислимой константы,
 * используя открытое статистическое завершенное поле.
 */
public class Item30
{
    public static void main(String[] args) {
        System.out.println(Apple.FUJI.toString());

        double earthWeight = 2.34;
        double mass = earthWeight / Planet.EARTH.surfaceGravity();

        for (Planet p : Planet.values())
        {
            System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
        }

        double x = 2.34;
        double y = 4.34;
        for(OperationUpdate op : OperationUpdate.values())
        {
            System.out.printf("%f %s %f = %f%n" , x, op, y, op.apply(x,y));
        }
    }
}

//простая форма
enum Apple{ FUJI, PIPPIN}
enum Orange{ NAVEL, TEMPLE, BLOOD}

//TODO Для ассоциации данных с перечисленными константами объявите поля экземпляра и напишите конструктор,
//TODO который возьмет данные и сохранит их в поля
enum Planet
{
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    private final double G = 6.67300E-11;
    Planet(double mass, double radius)
    {
        this.mass = mass;
        this.radius = radius;
        this.surfaceGravity = G*mass /(radius*radius);
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public double surfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;
    }

}


//Перечисляемый тип , переключающийся на свое собственное значение - не очень !!! ????????????
enum Operation
{
    //поломается если добавить NEW_OP
    PLUS,
    MINUS,
    TIMES,
    DIVIDE;

    double apply(double x, double y)
    {
        switch (this)
        {
            case PLUS: return x+y;
            case MINUS: return x-y;
            case TIMES: return x*y;
            case DIVIDE: return x/y;
        }
        throw new AssertionError("Unknow op : " + this);
    }
}

//Перечисляемый тип с реализацией метода в зависимости от констатны
enum OperationUpdate
{
    //
    PLUS("+") { double apply(double x, double y){ return  x + y; }},
    MINUS("-"){ double apply(double x, double y){ return  x - y; }},
    TIMES("*") { double apply(double x, double y){ return  x * y; }},
    DIVIDE("/"){ double apply(double x, double y){ return  x / y; }};

    private final String symbol;
    OperationUpdate(String symbol) {
        this.symbol = symbol;
    }

    abstract double apply(double x, double y);

    @Override
    public String toString() {
        return symbol;
    }

    //реализация метода fromString на перечисляемом типе
    private static final Map<String, OperationUpdate> stringToEnum = new HashMap<String, OperationUpdate>();
    //Initialize map from constant name to enum constants
    static
    {
        for(OperationUpdate op : values())
        {
            stringToEnum.put(op.toString(), op);
        }
    }

    //Return Operation for string , or null if string is invalid
    public static OperationUpdate fromString(String symbol)
    {
        return stringToEnum.get(symbol);
    }

}

//Перечислимый тип , переключающийся на свое значение , чтобы сделать код общим  - спорно
enum PayrollDay
{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    private static final int HOURS_PER_SHIFT = 8;
    double pay(double hoursWorked, double payRate)
    {
        double basePay = hoursWorked * payRate;
        double overtimePay;
        switch (this)
        {
            case SATURDAY: case SUNDAY:
                overtimePay = hoursWorked * payRate / 2;
            break;
            default:
                overtimePay = hoursWorked <= HOURS_PER_SHIFT ? 0 : (hoursWorked - HOURS_PER_SHIFT) * payRate / 2;
        }

        return basePay + overtimePay;
    }
}

//Стратегический перечислимый ?????? шаблон
//TODO Переключение на перечисляемых типах хорошо подходит для задания аргументов внешних перечисляемых типов
//TODO с помощью зависимых от констант реакций
enum PayrollDayUpdate
{
    MONDAY(PayType.WEEKDAY),
    TUESDAY(PayType.WEEKDAY),
    WEDNESDAY(PayType.WEEKDAY),
    THURSDAY(PayType.WEEKDAY),
    FRIDAY(PayType.WEEKDAY),
    SATURDAY(PayType.WEEKEND),
    SUNDAY(PayType.WEEKEND);

    private final PayType payType;
    PayrollDayUpdate(PayType payType)
    {
        this.payType = payType;
    }

    double pay(double hoursWorked, double payRate)
    {
        return payType.pay(hoursWorked, payRate);
    }

    private enum PayType
    {
        WEEKDAY
                {
                    double overtimePay(double hours, double payRate)
                    {
                        return hours <= HOURS_PER_SHIFT ? 0 : (hours - HOURS_PER_SHIFT) * payRate / 2;
                    }
                },
        WEEKEND
            {
                double overtimePay(double hours, double payRate)
                {
                    return hours * payRate / 2;
                }
            };

        private static final int HOURS_PER_SHIFT = 8;
        abstract double overtimePay(double hrs, double payRate);
        double pay(double hoursWorked, double payRate)
        {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }
}