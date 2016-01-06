package com.rybak.effective.java.ch6;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Используйте EnumMap вместо порядкового индексирования
 */
public class Item33
{
    public static void main(String[] args)
    {
        Herb[] garden = {new Herb("A", Herb.Type.ANNUAL),new Herb("B", Herb.Type.ANNUAL)} ;

//        Set<Herb>[] herbsByType = // Indexed by Herb.Type.ordinal()
//                (Set<Herb>[]) new Set[Herb.Type.values().length];
//        for (int i = 0; i < herbsByType.length; i++)
//            herbsByType[i] = new HashSet<Herb>();
//        for (Herb h : garden)
//            herbsByType[h.type.ordinal()].add(h);
//
//        // Print the results
//        for (int i = 0; i < herbsByType.length; i++) {
//            System.out.printf("%s: %s%n",
//                    Herb.Type.values()[i], herbsByType[i]);
//        }

        //Использование EnumMap для связи данных с перечеслимым типом
        Map<Herb.Type, Set<Herb>> herbsByType =
                new EnumMap<Herb.Type, Set<Herb>>(Herb.Type.class);
        for (Herb.Type t : Herb.Type.values())
            herbsByType.put(t, new HashSet<Herb>());

        for (Herb h : garden)
            herbsByType.get(h.type).add(h);

        System.out.println(herbsByType);




    }
}

class Herb {
    enum Type {ANNUAL, PERENNIAL, BIENNIAL}

    final String name;
    final Type type;

    Herb(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}


// Using ordinal() to index array of arrays - DON'T DO THIS!
//enum Phase { SOLID, LIQUID, GAS;
//    public enum Transition {
//        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
//        // Rows indexed by src-ordinal, cols by dst-ordinal
//        private static final Transition[][] TRANSITIONS = {
//                { null, MELT, SUBLIME },
//                { FREEZE, null, BOIL },
//                { DEPOSIT, CONDENSE, null }
//        };
//        // Returns the phase transition from one phase to another
//        public static Transition from(Phase src, Phase dst) {
//            return TRANSITIONS[src.ordinal()][dst.ordinal()];
//        }
//    }
//}


// Using a nested EnumMap to associate data with enum pairs
enum Phase {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
        final Phase src;
        final Phase dst;

        Transition(Phase src, Phase dst) {
            this.src = src;
            this.dst = dst;
        }
        // Initialize the phase transition map
        private static final Map<Phase, Map<Phase,Transition>> m =
                new EnumMap<Phase, Map<Phase,Transition>>(Phase.class);
        static {
            for (Phase p : Phase.values())
                m.put(p,new EnumMap<Phase,Transition>(Phase.class));
            for (Transition trans : Transition.values())
                m.get(trans.src).put(trans.dst, trans);
        }
        public static Transition from(Phase src, Phase dst) {
            return m.get(src).get(dst);
        }
    }
}