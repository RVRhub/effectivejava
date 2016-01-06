package com.rybak.effective.java.ch11.item78;

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


    //Агент сериализации класса Period
    private static class SerializationProxy implements Serializable {
        private final Date start;
        private final Date end;

        SerializationProxy(Period p) {
            this.start = p.start;
            this.end = p.end;
        }

        private static final long serialVersionUID = 234098243823485285L; // Any

        // readResolve method for Period.SerializationProxy
        private Object readResolve() {
            return new Period(start, end); // Uses public constructor
        }
    }

    // writeReplace method for the serialization proxy pattern
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    // readObject method for the serialization proxy pattern
    //защита
    private void readObject(ObjectInputStream stream)
            throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }
}