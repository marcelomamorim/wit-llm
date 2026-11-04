/* @test    @summary Test LinkedList forEachRemaining method for correct iteration and exception handling    @run main LinkedListWitupTest */

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = e -> sb.append(e);
        list.forEachRemaining(consumer);
        assert sb.toString().equals("123") : "Expected '123', got '" + sb.toString() + "'";
    }

    static void testForEachRemainingNullAction() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        try {
            list.forEachRemaining(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testForEachRemainingConcurrentModification() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Consumer<Integer> consumer = e -> {
            if (e == 2) {
                list.remove(); // structural modification
            }
        };
        try {
            list.forEachRemaining(consumer);
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
