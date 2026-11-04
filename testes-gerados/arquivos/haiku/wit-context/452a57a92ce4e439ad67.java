/*
 * @test
 * @summary Witup-generated behavioral tests for Vector.removeElementAt(int)
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveMiddleElement();
        testRemoveFirstElement();
        testRemoveLastElement();
        testRemoveFromSingleElementVector();
        testIndexEqualToSizeThrows();
        testIndexGreaterThanSizeThrows();
        testNegativeIndexThrows();
        testEmptyVectorIndexZeroThrows();
        testFailedRemovalLeavesVectorUnchanged();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testRemoveMiddleElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.removeElementAt(1);
        check(v.size() == 2, "size after removal");
        check("a".equals(v.get(0)), "element 0 preserved");
        check("c".equals(v.get(1)), "element shifted into index 1");
    }

    static void testRemoveFirstElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.removeElementAt(0);
        check(v.size() == 1, "size after removing first");
        check("b".equals(v.get(0)), "remaining element");
    }

    static void testRemoveLastElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.removeElementAt(1);
        check(v.size() == 1, "size after removing last");
        check("a".equals(v.get(0)), "remaining element");
    }

    static void testRemoveFromSingleElementVector() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.removeElementAt(0);
        check(v.isEmpty(), "vector empty after removing only element");
    }

    static void testIndexEqualToSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.removeElementAt(2);
            throw new AssertionError("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    static void testIndexGreaterThanSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        try {
            v.removeElementAt(5);
            throw new AssertionError("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.removeElementAt(-1);
            throw new AssertionError("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    static void testEmptyVectorIndexZeroThrows() {
        Vector<String> v = new Vector<>();
        try {
            v.removeElementAt(0);
            throw new AssertionError("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    static void testFailedRemovalLeavesVectorUnchanged() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.removeElementAt(2);
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
        check(v.size() == 2, "size unchanged after failed removal");
        check("a".equals(v.get(0)) && "b".equals(v.get(1)), "contents unchanged");
    }
}
