package com.rybak.effective.java.ch10;

/**
 * При работе с потоками документируйте уровень безопасности
 *
 * На самом деле класс может иметь несколько уровней безопасности. Чтобы класс можно было использовать в среде со многими потоками
 * в документации к нему должно быть четко указано, какой уровень безопасности он поддерживает.
 * - immutable
 * - thread-safe - экзамляры этого класса могут изменятся , однако все методы имеют довольно надежную внутреннюю синхронизацию
 * - conditionally-thread-safe - методы должны вызываттся один за другим без взаимного влияния без со стороны других потоков.
 * - no thread-safe
 * - thread-hosile - этот класс небезопасен при параллельной работе с несколькими потоками , даже если вызовы всех методов окружены
 * внешней синхронизацией. Обычно это когда поток меняет статические переменные.
 *
 * Для классов с conditionally-thread-safe - в документации важно указать , какой объект следует заблокировать ,
 * чтобы последовательность обращения к методам стала неделимой.
 */
public class Item70
{

    //Идеома закрытой блокировки объекта - припятсвует DOS-атаке
    private final Object lock = new Object();
    public void foo()
    {
        synchronized (lock)
        {

        }
    }
}
