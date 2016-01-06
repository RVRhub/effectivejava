package com.rybak.effective.java.ch6;

/**
 * Используйте поля экземпляра вместо числовых значений
 *
 * ordinal() только для структур данных , например EnumSet , EnumMap
 */
public class Item31
{

}

//Очень опасно.
enum Ensemble
{
    SOLO,
    DUET,
    TRIO;

    public int numberOfMusicians()
    {
        return ordinal() + 1;
    }

}

//Нормально.
enum EnsembleUpdate
{
    SOLO(1),
    DUET(2),
    TRIO(3);

    private final int numberOfMusicians;
    EnsembleUpdate(int numberOfMusicians)
    {
        this.numberOfMusicians = numberOfMusicians;
    }
    public int numberOfMusicians()
    {
        return numberOfMusicians;
    }

}

