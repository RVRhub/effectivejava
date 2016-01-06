package com.rybak.effective.java.ch10;

import sun.jvm.hotspot.oops.FieldType;

/**
 * C осторожностью используйте отложенную инициализацию
 *
 * Она уменьшает затраты на инициализацию класса или создания экземпляра за счет увеличения затрат на доступ к полю ,
 * инициализация которого была отложена .
 *
 * Используйте отложеную инициализацию для прерывания зациклиности инициализации (используйте синхоронезированный метод доступа).
 */
public class Item71
{
    //Нормальная иницализация экземпляра поля
    private final FieldType fieldType = computeFieldValue();

    //Отложеная инициализация поля экземпляра - синхронезированый метод доступа
    private FieldType field;
    synchronized FieldType getField()
    {
        if(field == null)
        {
            field = computeFieldValue();
        }
        return field;
    }

    //Если вам требуется отложенная инициализация статистического поля для производительности , используйте идиому ,
    //содержащую отложенную инициализацию класса.
    //Идиома отложенной инициализации класса для статического поля
    private static class FieldHolder
    {
        static final FieldType fieldH = computeFieldValue();
    }

    static FieldType getFieldH()
    {
        return FieldHolder.fieldH;
    }

    //Если вам требуется отложенная инициализация для повышения производительности поля экземляра
    //, используйте идиому двойной проверки.
    private volatile FieldType fieldV;
    FieldType getFieldV()
    {
        FieldType result = fieldV;
        if(result == null)
        {
            synchronized (this)
            {
                result = fieldV;
                if(result == null)
                {
                    fieldV = result = computeFieldValue();
                }
            }
        }

        return result;
    }

    // Идиома одной краткой проверки
    private volatile FieldType fieldV2;
    FieldType getFieldV2()
    {
        FieldType result = fieldV2;
        if(result == null)
        {
            fieldV2 = result = computeFieldValue();
        }

        return result;
    }


    private static FieldType computeFieldValue() {
        return null;
    }
}
