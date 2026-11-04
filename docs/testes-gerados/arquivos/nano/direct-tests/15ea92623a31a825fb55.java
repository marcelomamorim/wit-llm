/*
 * @test
 * @summary Test ArrayList forEach method for correctness and exception handling
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachNormal();
        testForEachNullAction();
        testForEachConcurrentModification();
    }

    static void testForEachNormal() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        list.forEach(i -> sb.append(i));
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }

    static void testForEachNullAction() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        boolean exceptionThrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was not thrown for null action";
    }

    static void testForEachConcurrentModification() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                if (i == 2) {
                    list.add(4); // structural modification during iteration
                }
            }
        };
        boolean exceptionThrown = false;
        try {
            list.forEach(consumer);
        } catch (ConcurrentModificationException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "ConcurrentModificationException was not thrown during concurrent modification";
    }
}
