package com.rybak.effective.java.ch11.item76;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public final class Period implements Serializable {
    private Date start;
    private Date end;

    /**
     *
     * @param start начало периода
     * @param end   конец периода , не должен предшествовать началу периода
     * @throws IllegalArgumentException if начало перода указано после конца
     * @throws NullPointerException если начало или конец указаны нулевые
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(start + " after " + end);
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    public String toString() {
        return start + " - " + end;
    }

    // readObject method with validity checking
    // This will defend against BogusPeriod attack but not MutablePeriod.
//     private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
//     {
//         s.defaultReadObject();
//
//         // Check that our invariants are satisfied
//         if (start.compareTo(end) > 0)
//             throw new InvalidObjectException(start +" after "+ end);
//     }

     //Метод readObject c резервным копирование и проверкой правильности
     private void readObject(ObjectInputStream s)
             throws IOException, ClassNotFoundException
     {
         s.defaultReadObject();

         // Defensively copy our mutable components
         start = new Date(start.getTime());
         end = new Date(end.getTime());

         // Check that our invariants are satisfied
         if (start.compareTo(end) > 0)
             throw new InvalidObjectException(start +" after "+ end);
     }
}