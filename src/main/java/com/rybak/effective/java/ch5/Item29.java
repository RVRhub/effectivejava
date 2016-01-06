package com.rybak.effective.java.ch5;


import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Использование неоднородных контейнеров
 */
public class Item29
{
    public static void main(String[] args)
    {
        Favorites f = new Favorites();
        f.putFavorite(String.class, "Java");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Class.class);

        String favoriteString = f.getFavorite(String.class);
        int favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getSimpleName());

    }
}


//TODO класс Favorites безопасный с точки зрения типов
//TODO неоднородный , все ключи являются ключами разных типов
//TODO следовательно мы можем назвать Favorites безопасным неоднородным контейнером
//Безопасный шаблон неоднородного контейнира - API
class Favorites
{
    private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();

    //Безопасность типов обеспечивается динамичексой передачей
    //TODO имеются также упаковщи колекций Collections.checkedSet()  Collections.checkedList()

    public <T> void putFavorite(Class<T> type, T instance)
    {

        if(type == null)
        {
            throw new NullPointerException("Type is null");
        }
        favorites.put(type, type.cast(instance));
    }

    public <T> T getFavorite(Class<T> type)
    {
        //cast проверяет , а является объект экземляром типа type
        return type.cast(favorites.get(type));
    }

    public Annotation getAnnotation(AnnotatedElement element, String annotationTypeName)
    {
        Class<?> annotationType = null;
        try
        {
            annotationType = Class.forName(annotationTypeName);
        }
        catch (Exception ex)
        {
            throw new IllegalArgumentException(ex);
        }

        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }
}