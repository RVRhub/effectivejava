package com.rybak.effective.java.ch11.item76;

import java.io.*;
import java.util.Date;

/**
 * Метод readObject должен создаваться с защитой
 *
 * readObject - работает как конструктор, очень важно делать защиту объектов
 *
 * writeUnshared и readUnshared - лучше не использовать , были добавлены в версии 1,4 , с целью препятствия
 * неконтролируемым атакам ссылок на объекты за счет резервного копирования
 *
 *
 * ObjectInterfaceValidator - для валидации после десериализации целого графа объектов
 */
public class Item76
{

}




