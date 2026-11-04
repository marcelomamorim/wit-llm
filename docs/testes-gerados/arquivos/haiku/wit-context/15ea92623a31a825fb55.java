/*
 * @test
 * @summary Tests ArrayList.forEach null action handling, element traversal order and ConcurrentModificationException
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullActionThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) must throw NullPointerException");
    }

    static void testNullActionThrowsNPEOnEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        boolean thrown = false;
        try {
            list.forEach(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEach(null) on empty list must throw NullPointerException");
    }

    static void testVisitsElementsInOrder() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(1);
        list.add(4);
        List<Integer> seen = new ArrayList<>();
        list.forEach(seen::add);
        check(seen.equals(list), "forEach must visit elements in order: " + seen);
    }

    static void testEmptyListNoInvocation() {
        ArrayList<String> list = new ArrayList<>();
        int[] count = {0};
        list.forEach(s -> count[0]++);
        check(count[0] == 0, "forEach on empty list must not invoke action");
    }

    static void testNullElementsVisited() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("x");
        List<String> seen = new ArrayList<>();
        list.forEach(seen::add);
        check(seen.size() == 2 && seen.get(0) == null && "x".equals(seen.get(1)),
              "forEach must visit null elements");
    }

    static void testConcurrentModificationDetected() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        boolean thrown = false;
        try {
            list.forEach(s -> {
                if (s.equals("a")) {
                    list.add("d");
                }
            });
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "structural modification during forEach must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionThrowsNPEOnEmptyList();
        testVisitsElementsInOrder();
        testEmptyListNoInvocation();
        testNullElementsVisited();
        testConcurrentModificationDetected();
    }
}
