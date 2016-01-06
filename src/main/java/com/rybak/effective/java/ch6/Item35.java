package com.rybak.effective.java.ch6;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Предпочитайте аннотации шаблонам присвоения имен.
 *
 * До версии 5 было общепринятым использование именование шаблонов (naming pаttern) для определения ,
 * так как некоторые элементы программы требуют специального «подхода»: инструментом или структурой.
 *
 * Аннотация не для текущего поля/метода , а для того кто ее будет использовать:
 */
public class Item35
{
    //Программа для обработки аннотаций с маркерами
    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("com.rybak.effective.java.ch6.Sample2");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                ++tests;
                try {
                    m.invoke(null);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + " failed: " + exc);
                } catch (Exception exc) {
                    System.out.println("INVALID @Test: " + m);
                }
            }
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                ++tests;
                try {
                    m.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", m);
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    Class<? extends Exception>[] excTypes = m.getAnnotation(ExceptionTest.class).value();
                    int oldPassed = passed;
                    for (Class<? extends Exception> excType : excTypes) {
                        if (excType.isInstance(exc)) {
                            ++passed;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s%n", m, exc);
                    }
                } catch (Exception exc) {
                    System.out.println("INVALID @Test: " + m);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}

class Sample {

    @Test
    public static void m1() {
    }

    public static void m2() {
    }

    //test should be fail
    @Test
    public static void m3() {
        throw new RuntimeException("Boom");
    }

    public static void m4() {
    }

    //invalid use: nonstatic method
    @Test
    public void m5() {
    }

    public static void m6() {
    }

    @Test
    public static void m7() {
        throw new RuntimeException("Crash");//test should be fail
    }

    public static void m8() {
    }
}

class Sample2 {
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }
    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }
    @ExceptionTest(ArithmeticException.class)
    public static void m3() {
    }
    @ExceptionTest({IndexOutOfBoundsException.class, NullPointerException.class})
    public static void m4() {
        List<String> list = new ArrayList<String>();
        list.addAll(5, null);
    }
}