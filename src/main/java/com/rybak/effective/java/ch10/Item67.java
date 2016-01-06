package com.rybak.effective.java.ch10;

import com.sun.tools.classfile.Opcode;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Избегайте избыточной синхронизации
 *
 * Для исключения возможности взаимной блокировки (deadlock) никогда не передавайте управление клиенту,
 * если находитесь в синхронизированном методе или блоке.
 *
 * Приемы для достижения параельности : разделение блокировки , распределение блокировки и контроль паралельности без блокеровки .
 *
 * Если классы или статические методы связаны с изменяемыми статическими полями , он должен иметь внутреннюю синхронизацию,
 * даже если обычно применяются только с одним поток. В противоположность совместно используемому экземпляру здесь клиент
 * не имеет возможности провести внешнюю синхронизацию , посколько нет никакой гарантии , что другие клиенты будут делать тоже самое .
 *
 * Никога не вызывайте чужые методы из синхронизируемой области.
 */
public class Item67
{
    public static void main(String[] args) {
        ObservableSet<Integer> set =
                new ObservableSet<Integer>(new HashSet<Integer>());

//        set.addObserver(new SetObserver<Integer>() {
//            public void added(ObservableSet<Integer> s, Integer e) {
//                System.out.println(e);
//                if (e == 23) s.removeObserver(this);
//            } });

        //Наблюдатель испльзует без надобный фоновый поток
        set.addObserver(new SetObserver<Integer>() {
            public void added(final ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    final SetObserver<Integer> observer = this;
                    try {
                        executor.submit(new Runnable() {
                            public void run() {
                                s.removeObserver(observer);
                            }
                        }).get();
                    } catch (ExecutionException ex) {
                        throw new AssertionError(ex.getCause());
                    } catch (InterruptedException ex) {
                        throw new AssertionError(ex.getCause());
                    } finally {
                        executor.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++)
            set.add(i);
    }
}

class ObservableSet<E> extends ForwardingSet<E>
{
    public ObservableSet(Set<E> set)
    {
        super(set);
    }


    //TODO Thread-safe observable set with CopyOnWriteArrayList - best variant for Observer
//    private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<SetObserver<E>>();
//    public void addObserver(SetObserver<E> observer) {
//        observers.add(observer);
//    }
//    public boolean removeObserver(SetObserver<E> observer) {
//        return observers.remove(observer);
//    }
//    private void notifyElementAdded(E element) {
//        for (SetObserver<E> observer : observers)
//            observer.added(this, element);
//    }


    private final List<SetObserver<E>> observers = new ArrayList<SetObserver<E>>();

    public void addObserver(SetObserver<E> observer)
    {
        synchronized (observers)
        {
            observers.add(observer);
        }
    }

    public void removeObserver(SetObserver<E> observer)
    {
        synchronized (observers)
        {
            observers.remove(observer);
        }
    }

    //todo - Ошоибка запускает чужой метод из синхронизируемого блока
    private void notifyElementAdded(E element) {
//        synchronized(observers) {
//            for (SetObserver<E> observer : observers)
//                observer.added(this, element);
//        }

        //Чужой метод перемещен за пределы синхронизационого блока - открытые вызовы
        List<SetObserver<E>> snapshot = null;
        synchronized(observers) {
            snapshot = new ArrayList<SetObserver<E>>(observers);
        }
        for (SetObserver<E> observer : snapshot)
            observer.added(this, element);
    }

    @Override
    public boolean add(E element)
    {
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }
    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        boolean result = false;
        for (E element : c)
            result|=add(element); //callsnotifyElementAdded return result;

        return result;
    }

}

class ForwardingSet<E> extends HashSet<E>{
    public <E> ForwardingSet(Set<E> set) {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}

interface SetObserver<E>
{
    void added (ObservableSet<E> set, E element);
}
