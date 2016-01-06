package com.rybak.effective.java.ch9;

import java.util.ConcurrentModificationException;

/**
 * Предпочитайте стандартные исключения
 *
 * IllegalArgumentException - Неправильное значение параметров
 * IllegalStateException    - Состояние объекта неприемлемо для вызова методов
 * IndexOutOfBoundsException - Значения параметра , задающего индекс, выходит за приделы диапозона
 * NullPointerException     - Значение параметра равно  null
 * ConcurrentModificationException - Обнаружена параллельная модификация объекта из разных потоков
 * UnsupportedOperationException   - Объект не имеет поддержки указанного метода
 */
public class Item60 {

}
