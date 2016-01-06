package com.rybak.effective.java.ch11;

import java.io.*;

/**
 * Рассмотрите возможность использования специализированной сериализованной формы
 *
 * Нельзя принимать сериализованную форму, предлагаемою по умолчанию , не обдумав как следует , устраивает ли она вас.
 *
 * Сериализованная форма, предлагаемая по умолчанию, по-видимому, будет приемлема в том случае, если физическое представление
 * объекта равно значно его лочическому содержанию.
 *
 * Если вы решите принять сериализованную форму, предлагаемую по умолчанию, во многих случаях сохранение инвариантов и
 * безопасность требубт реализации метода readObject
 *
 * В случае, когда физическое представление объекта существено отличается от содержащихся в нем логических данных,
 * сериализованная форма, предлагаемая по умолчанию , имеет четыре недостатка:
 *  - Она навсегда связывает внешний API класса с его текущим втрутренним представлением .
 *  - Она может занимать чрезвычано много места
 *  - Она может обрабатываться чрезвычайно долго
 *  - Она может вызвать переполнения стека
 *
 * Прежде чем согласиться на запись какого-либо поля в сериализованной форме , убедитеть в том , что его значение является
 * частью логического состояния данного объекта.
 */
public class Item75
{
}

//Хороший кандидат для испльзования формы, предлагаемой по умолчанию
class Name implements Serializable
{
    /**
     * Last Name - не должно быть пустым  (non-null)
     * @serial  - это говорит о том , что эту информацию надо поместить на спец страницу , где описываются сериализованные формы.
     */
    private String lastName;

    /**
     * First Name - не должно быть пустым  (non-null)
     * @serial
     */
    private String firstName;

    /**
     * Инициал или '\u0000' , если инициал отсутсвует
     * @serial
     */
    private char middleInitial;
    //Остальное опущено
}

//Ужасный кадидат на использование сериализованной формы, предлагаемой по умолчанию
class StringList implements Serializable
{
    private int size = 0;
    private Entry head = null;

    private static class Entry implements Serializable
    {
        String data;
        Entry next;
        Entry previous;
    }

    //Остальное опущено
}

//Класс StringList с правильной сериализованой формой
final class StringListGood implements Serializable
{
    private transient int size = 0;
    private transient Entry head = null;

    //Больше нет реализации Serializable
    private static class Entry
    {
        String data;
        Entry next;
        Entry previous;
    }

    //Добавляет указанную строку в конце списка
    public void add(String s){}

    //writeObject для синхронизированного класса
    /**
     * Сериализует данный экземпляр {@code StringListGood}
     * @serialData Показывается размер списка (количество сожержащихся в нем строк) ({@code int},  за которым в праильной
     * последовательности следуют все элементы списка (каждый в виде {@code String})
     */
    private void writeObject(ObjectOutputStream s) throws IOException
    {
        s.defaultWriteObject();
        s.writeObject(size);

        //Выписываем все элементы в правильном порядке
        for(Entry e = head; e != null; e = e.next)
        {
            s.writeObject(e.data);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        int size = s.readInt();

        //Считываем все элементы и вставляем их в список
        for(int i = 0; i < size; i++)
        {
            add((String) s.readObject());
        }
    }

    //Остальное опускаем
}



