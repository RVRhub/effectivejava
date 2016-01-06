package com.rybak.effective.java.ch7;

/**
 * Для всех открытых элементов API пишите doc-комментарии
 *
 * Sun how to write doc comments
 *
 * Чтобы должным образом документировать API, следует предварять doc-комментарием каждую представляемую пользователем
 * деклларацию класса, интерфейса, конструктора, метода или поля.
 *
 * Doc-комментарий для метода должен лаконично описывать согласия между этими методами и его клиентами.
 * Что делает данный метод? 
 * Прекондишен @throws Посткондишен
 *
 * Безопасность класса при работе с потоками
 *
 * @param   - представляют собой именную конструкцию
 * @return  - по-анлийски noun phrase
 *
 * @throw должен состоять из слов if
 *
 * Когда вы пишете документацию по обобщенным типам или методам , обязательно документируйте все параметры типа:
 *
 * При документировании аннотационых типов убедитесь, что документирован любой член , так же как и сам тип.
 */
public class Item44<E>
{

    /**
     *
     * Методы глагольных конструкций создают , возвращают.
     *
     * Классы или интерфейсы или поля должны быть именной конструкции.
     *
     * [summary description]Возвращает элемент, который занимает заданную позицию в данном списке.
     * точку желательно в numeric encoding &#46
     *
     * <p>Этот метод <i>не </i> дает гарантии, что будет выполнятся в постоянное время
     * В некоторых реализациях он будет выполнятся во время, пропорциональное положению его элементов.</p>
     *
     * The triangle inequality is {@literal |x + y| < |x| + |y| }
     *
     * @param index индекс элемента который нужно возвратить , индекс должен быть меньше размера списка и не отрицательным
     * @return элемент, занимающий в списке нужную позицию
     * @throws IndexOutOfBoundsException if индекс лежит в не диапозона
     *       ({@code index < 0 || index >= this.size()})
     */
    public E get(int index)
    {
        return null;
    }

}

/**
 * An object that maps keys to values. A map cannot contain
 * duplicate keys; each key can map to at most one value.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
interface Map<K, V>
{
    //...Remainder omitted
}

/**
 * An instrument section of a symphony orchestra.
 *
 * При документировании перечислимого типа убедитесь,
 * что документированны константы , тип и любой открытый метод.
 */
enum OrchestraSection
{
    /** Woodwind, such as flute, clarinet, and oboe */
    WOODWIND,
    /** Brass instruments, such as french horn */
    BRASS,
    /** Percussion instruments, such as timpani */
    PERCUSSION,
    /** String instruments, such as violin */
    STRING
}
