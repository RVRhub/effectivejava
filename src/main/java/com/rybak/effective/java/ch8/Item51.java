package com.rybak.effective.java.ch8;

/**
 * При канкатенации строк опасайтесь потери производительности
 *
 * Время , которое необходимо оператору конкатенации для последовательного объединения n строк, пропорционально
 * квадрату числа n.
 *
 */
public class Item51
{

    //неуместное объединение строк -- прлохая производительность
    public String statement()
    {
        String result = "";
        for(int i = 0; i < 5; i++)
        {
            result += lineForItem(i);
        }
        return result;
    }

    private String lineForItem(int i) {
        return "test";
    }

    public String statementBetter()
    {
        StringBuilder b = new StringBuilder(5);
        for(int i = 0; i < 5; i++)
        {
            b.append(lineForItem(i));
        }
        return b.toString();
    }


}
