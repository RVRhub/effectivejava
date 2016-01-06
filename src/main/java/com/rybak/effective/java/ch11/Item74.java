package com.rybak.effective.java.ch11;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Соблюдайте осторожность при реализации интерфейса Serializable
 *
 * Дизассемблирование скомпилированного class-файла можно произвести с помощью команды: javap -c <имя класса>
 *
 * Значительная доля затрат на рефлизацию интерфейса Serializable связана с тем, что это уменьшается возможность
 * изменения реализации класса в последующих версиях.
 *
 * Второе неудобство от рефлизации интерфейса Serializable заключается в том , что повышается вероятность появления
 * ошибок и дыр в защите.
 *
 * Третим неудобством реализации интерфейса Serializable связано с тем , выпуск новой версии класса сопряжен с с большой
 * работой по тестированию.
 *
 * Классы, предназначеные для наследования (статья 17) , редко должны рефлоизовывать Serializable , а интрефейсы - редко
 * его расширять.
 *
 * Для нессериализуемого класса , который предназанчен для наследования, вы должны рассмотреть
 * возможность создания конструкора без параметров.
 *
 */
public class Item74 implements Serializable
{
    //TODO Самое лучшее - это создавать объекты , у которых все инварианты уже установлены (статья 15).

    //readObjectNoData для расширяемых сериализуемых классов (детали в спеке)
    private void readObjectNoData() throws InvalidObjectException
    {
        throw new InvalidObjectException("Stream data required");
    }

}

//Несериализуемый, имеющий состояние класс, для которого можно создать подклассы
abstract class AbstractFoo
{
   private int x,y;

    private enum State{ NEW, INITIALIZING, INITIALIZED};
    private final AtomicReference<State> init = new AtomicReference<State>(State.NEW);

    public AbstractFoo(int x, int y)
    {
        initialize(x,y);
    }

    //это позволяет создавать классы которые будут Serializable
    protected AbstractFoo(){}

    protected final void initialize(int x, int y)
    {
        if(!init.compareAndSet(State.NEW, State.INITIALIZING)) {
            throw new IllegalStateException("Already initialized");
        }
        this.x = x;
        this.y = y;

        init.set(State.INITIALIZED);
    }

    protected final int getX()
    {
        checkInit();
        return x;
    }
    protected final int getY()
    {
        checkInit();
        return y;
    }

    private void checkInit() {
        if(init.get() != State.INITIALIZED)
        {
            throw new IllegalStateException("Uninitialized");
        }

        //остальное опущено
    }
}


//Сериализуемый подкласс несериализуемого класса , имещющего состояние
class Foo extends AbstractFoo implements Serializable
{
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();

        //ручная десериализация и инициализация состояния суперкласса
        int x = s.readInt();
        int y = s.readInt();
        initialize(x,y);

    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        //Ручная сериализация состояния суперкласса
        s.writeInt(getX());
        s.writeInt(getY());
    }

    //Конаструктор не используется никаких приудливых механизмов
    public Foo(int x, int y)
    {
        super(x,y);
    }

    public static final long serialVersionUID = 18589839223424L;

    //TODO Внутренние классы (статья 22) редко должны (если вообще должны) рефлизовывать интерфейс Serializable.
}
