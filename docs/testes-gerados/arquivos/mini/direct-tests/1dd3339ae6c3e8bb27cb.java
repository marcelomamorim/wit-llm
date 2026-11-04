/*
 * @test
 * @summary Test java.util.Vector add(int index, E element) method
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testAddAtValidIndex();
        testAddAtZeroIndexEmptyVector();
        testAddAtEndIndex();
    }

    private static void testAddAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.add(1, "x");
        if (!"x".equals(v.get(1))) {
            throw new AssertionError("Element at index 1 should be 'x'");
        }
        if (v.size() != 4) {
            throw new AssertionError("Size should be 4 after add");
        }
    }

    private static void testAddAtZeroIndexEmptyVector() {
        Vector<Integer> v = new Vector<>();
        v.add(0, 10);
        if (v.size() != 1) {
            throw new AssertionError("Size should be 1 after add at index 0");
        }
        if (!Integer.valueOf(10).equals(v.get(0))) {
            throw new AssertionError("Element at index 0 should be 10");
        }
    }

    private static void testAddAtEndIndex() {
        Vector<String> v = new Vector<>();
        v.add("first");
        v.add("second");
        v.add(2, "third");
        if (!"third".equals(v.get(2))) {
            throw new AssertionError("Element at index 2 should be 'third'");
        }
        if (v.size() != 3) {
            throw new AssertionError("Size should be 3 after add at end");
        }
    }
}
