package com.rybak.effective.java.ch9;

import java.util.EmptyStackException;

/**
 * Добивайтесь атомарности методов по отношению к сбоям
 *
 *
 * Вызов метода, завершившийся сбоем , должен оставлять обрабатываемый объект в том же состоянии, в котором был перед вызовом.
 * - Для этого надо все проверки делать до изменения объекта.
 * - Создавать копью объекта и вконце метода делать перезапись
 * - Рекавари объекта , востанваливать объект , делать откат
 * -
 */
public class Item64
{
    private Object[] elements;

    public Object pop()
    {
        if (size() == 0)
            throw new EmptyStackException();

        Object result = elements[-size()];
        elements[size()] = null; // Убираем устаревшую ссылку
        return result;
    }

    private int size() {
        return 0;
    }
}
