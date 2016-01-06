package com.rybak.effective.java.ch5;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Предпочитайте списки массивам
 */

//Ковариантность, если Sub является подтипом Super - тогда тип массива Sub[] является подтипом Super[].
//Средства обобщенного программирования ИНВАРИАНТНЫ , это говорит о том , что для любых двух типов Type1 и Type2 ,
// List<Type1> не является ни подтипом , ни превосходящим типом для List<Type2>.
public class Item25
{

    static void badCode()
    {
        //Выводит ошибку при запуске
        Object[] objectArray = new Long[1];
        objectArray[0] = "I do't fit in ";

        //Не компилировать
//        List<Object> ol = new ArrayList<Long>();
//        ol.add("I do't fit in ");

//        List<String>[] stringList = new List<String>[1]; //это оплохо
//        List<Integer> intList = Arrays.asList(42);
//        Object [] objects = stringList;
//        objects[0] = intList;
//        String s = stringList[0].get(0);

    }

    //TODO Массивы знают и выполняют свои типы элементов при выполнении.
    //TODO Средства обобщенного программирования , напротив, реализуются стиранием. Это значит то,
    //TODO что они выполняют свои ограничения типов только на этапе компиляции,
    //TODO а затем выбрасывают информацию о типах элементов при выполнении.
    public static void main(String[] args) {

    }

    //TODO  E, List<E>, List<String> - нематериальные типы, это такие типы ,
    //TODO  представление которых содержит меньше информации при выполнении , чем при компиляции.

    static Object reduce(List list , Function f, Object initVal)
    {
        synchronized (list)
        {
            Object result = initVal;
            for(Object o : list)
            {
                result = f.apply(result, o);
            }
            return result;
        }
    }
//убрали недостаток параллелизма
    static Object reduceParalel(List list , Function f, Object initVal)
    {
        Object[] snapshot = list.toArray(); //Lock list internally
        Object result = initVal;
        for(Object o : snapshot)
        {
            result = f.apply(result, o);
        }
            return result;

    }

    interface Function {
         Object apply (Object result, Object o);
    }

    interface FunctionGeneric<T> {
        T apply (T result, T o);
    }

    //Лучшие метод reduce
    static <E> E reduceTheBest(List<E> list , FunctionGeneric<E>  f, E initVal)
    {
        List<E> snapshot;
        synchronized (list)
        {
            snapshot = new ArrayList<E>(list);
        }

        E result = initVal;
        for(E o : snapshot)
        {
            result = f.apply(result, o);
        }
        return result;

    }
}
