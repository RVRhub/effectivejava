package com.rybak.effective.java.ch11.item76;

import com.rybak.effective.java.ch11.item76.Period;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * MutablePeriod
 */
public class MutablePeriod
{
    //Экземпляр интервала времени
    public final Period period;

    //Поле начало периода , к которому мы не должны иметь доступ
    public final Date start;
    //Поле конца периода , к которому мы не должны иметь доступ
    public final Date end;

    public MutablePeriod()
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);

            //Сериализуем правильный экземпляр Period
            out.writeObject(new Period(new Date(), new Date()));

            /**
             * Добавляем в конце неконтролируемые "ссылки на предыдущие объекты"
             * для внутрених полей Date в экземпляре Period. Подробнее см
             * Java Object Serialization Specification раздел 6.4
             */
            byte[] ref = { 0x71, 0, 0x7e, 0, 5 };  // сылка №5
            bos.write(ref); //поле start
            ref[4] = 4;     //ссылка №4
            bos.write(ref); //поле end


            //Десериализация экземпляра Period и "украденых" ссылок на экземпляр Date
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            period = (Period) in.readObject();
            start = (Date) in.readObject();
            end = (Date) in.readObject();

        }
        catch (Exception e)
        {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date pEnd = mp.end;

        // Let's turn back the clock
        pEnd.setYear(78);
        System.out.println(p);

        // Bring back the 60s!
        pEnd.setYear(69);
        System.out.println(p);
    }

}
