package com.rybak.effective.java.ch7;

import java.util.Date;

/**
 * При необходимости содавайте резервные копии
 *
 * Вы должны писать программы с защитой, исходя из предположения, что клиенты вашего класса будут
 * предпринимать все возможно для того , чтобы разрушить его инварианты.
 *
 */
public class Item39
{
    public static void main(String[] args) {
        // Атака на содержимое экземпляра Period
        Date start = new Date();
        Date end = new Date();
        Period p = new Period(start, end);
        System.out.println(p.getEnd());
        end.setYear(78);
        System.out.println(p.getEnd());

        //Для защиты от такого типа нападений,
        //Для каждого изменяемого параметра конструктор должен
        //создавать резервную копию и использовать именно ее
    }
}


final class Period
{
    private final Date start;
    private final Date end;

    /**
     *
     * @param start - начало периода
     * @param end - конец периода, который не должен предшествовать началу
     * @throws IllegalArgumentException - если start позже, чем end
     * @throws NullPointerException - если start или end равно null
     */
    public Period(Date start, Date end)
    {
//        if(start.compareTo(end) > 0)
//            throw new IllegalArgumentException(start + " after " + end);
//
//        this.start = start;
//        this.end = end;


        //Резервные копии создаются до проверки правильности параметров, так что сама проверка выполняется уже не для
        //оригинала, а для его копии.
        //TODO Чтобы предотвратить атаки такого рода, не используйте метод clone для создания резервной копии параметра,
        //TODO который имеет тип , позволяющий ненадежным параметрам создавать подклассы.
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if(start.compareTo(end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
    }

    //возвращать резервные копии изменяемых внутренних полей
    public Date getStart()
    {
        return (Date)start.clone();
    }

    public Date getEnd()
    {
        return (Date)end.clone();
    }
}
