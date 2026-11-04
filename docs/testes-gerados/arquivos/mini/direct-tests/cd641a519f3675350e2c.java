/*
 * @test
 * @summary Test java.util.Vector.insertElementAt(E obj, int index) method
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testInsertAtValidIndex();
        testInsertAtZeroIndexEmptyVector();
        testInsertAtEndIndex();
        testInsertAtIndexGreaterThanElementCount();
    }

    private static void testInsertAtValidIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.insertElementAt("x", 1);
        // After insertion, element at index 1 should be "x"
        if (!"x".equals(v.get(1))) {
            throw new RuntimeException("testInsertAtValidIndex failed: element at index 1 is not 'x'");
        }
        // Size should be 4
        if (v.size() != 4) {
            throw new RuntimeException("testInsertAtValidIndex failed: size is not 4");
        }
        // Elements after index 1 shifted right
        if (!"b".equals(v.get(2)) || !"c".equals(v.get(3))) {
            throw new RuntimeException("testInsertAtValidIndex failed: elements after index 1 not shifted correctly");
        }
    }

    private static void testInsertAtZeroIndexEmptyVector() {
        Vector<Integer> v = new Vector<>();
        v.insertElementAt(10, 0);
        if (v.size() != 1) {
            throw new RuntimeException("testInsertAtZeroIndexEmptyVector failed: size is not 1");
        }
        if (!Integer.valueOf(10).equals(v.get(0))) {
            throw new RuntimeException("testInsertAtZeroIndexEmptyVector failed: element at index 0 is not 10");
        }
    }

    private static void testInsertAtEndIndex() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.insertElementAt("c", 2); // index == elementCount
        if (v.size() != 3) {
            throw new RuntimeException("testInsertAtEndIndex failed: size is not 3");
        }
        if (!"c".equals(v.get(2))) {
            throw new RuntimeException("testInsertAtEndIndex failed: element at index 2 is not 'c'");
        }
    }

    private static void testInsertAtIndexGreaterThanElementCount() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.insertElementAt("x", 3); // elementCount is 2, index 3 > 2
            throw new RuntimeException("testInsertAtIndexGreaterThanElementCount failed: expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("3 > 2")) {
                throw new RuntimeException("testInsertAtIndexGreaterThanElementCount failed: exception message incorrect");
            }
        }
    }
}
