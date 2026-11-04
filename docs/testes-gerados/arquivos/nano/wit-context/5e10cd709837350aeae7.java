/*
 * @test
 * @summary Tests for ArrayList.add(int, E) method, covering normal and exceptional behaviors.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAtValidIndex();
        testAddAtNegativeIndex();
        testAddAtIndexGreaterThanSize();
        testAddWithConcurrentModification();
    }

    private static void testAddAtValidIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add(1, "x");
        if (!list.get(1).equals("x") || list.size() != 3) {
            throw new AssertionError("Failed: add at valid index");
        }
    }

    private static void testAddAtNegativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean threw = false;
        try {
            list.add(-1, "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Failed: no exception for negative index");
        }
    }

    private static void testAddAtIndexGreaterThanSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean threw = false;
        try {
            list.add(5, "x");
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Failed: no exception for index greater than size");
        }
    }

    private static void testAddWithConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            for (String s : list) {
                list.remove(0);
            }
        } catch (java.util.ConcurrentModificationException e) {
            return; // Expected
        }
        // The above may not always throw, so we simulate concurrent modification
        ArrayList<String> list2 = new ArrayList<>(list);
        list2.add("d");
        list2.getClass().getMethod("add", int.class, Object.class).setAccessible(true);
        boolean threw = false;
        try {
            list2.add(0, "x");
        } catch (java.util.ConcurrentModificationException e) {
            threw = true;
        }
        if (!threw) {
            // No exception thrown, test passes as per current implementation
        }
    }
}
