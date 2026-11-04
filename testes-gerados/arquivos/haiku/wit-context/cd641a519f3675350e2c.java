/*
 * @test
 * @summary WITUP-derived checks for Vector.insertElementAt bounds handling
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testInsertAtSizeAppends();
        testInsertAtZeroIntoEmpty();
        testInsertBeyondSizeThrows();
        testFailedInsertLeavesStateUnchanged();
    }

    static void testInsertAtSizeAppends() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.insertElementAt("c", 2);
        check(v.size() == 3, "size after append at index == size");
        check("a".equals(v.get(0)) && "b".equals(v.get(1)) && "c".equals(v.get(2)),
              "contents after append at index == size");
    }

    static void testInsertAtZeroIntoEmpty() {
        Vector<String> v = new Vector<>();
        v.insertElementAt("x", 0);
        check(v.size() == 1 && "x".equals(v.get(0)), "insert at 0 into empty vector");
    }

    static void testInsertBeyondSizeThrows() {
        Vector<String> v = new Vector<>();
        try {
            v.insertElementAt("x", 3);
            fail("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("3 > 0".equals(e.getMessage()), "exception message for index > elementCount");
        }
    }

    static void testFailedInsertLeavesStateUnchanged() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.insertElementAt("b", 5);
            fail("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        check(v.size() == 1 && "a".equals(v.get(0)), "vector unchanged after failed insert");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException("Failure: " + message);
    }
}
