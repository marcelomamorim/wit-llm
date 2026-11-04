/*
 * @test
 * @summary Tests Vector.removeElementAt shifting, size updates, bounds checks and fail-fast behavior
 * @run main VectorWitupTest
 */

import java.util.ArrayIndexOutOfBoundsException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveFirstShiftsElements();
        testRemoveMiddleShiftsElements();
        testRemoveLastDecrementsSize();
        testIndexEqualsSizeThrows();
        testIndexBeyondSizeThrows();
        testNegativeIndexThrows();
        testEmptyVectorThrows();
        testStructuralModificationInvalidatesIterator();
    }

    static Vector<String> abc() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        return v;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testRemoveFirstShiftsElements() {
        Vector<String> v = abc();
        v.removeElementAt(0);
        check(v.size() == 2, "size after remove first");
        check("b".equals(v.get(0)), "first element after remove first");
        check("c".equals(v.get(1)), "second element after remove first");
    }

    static void testRemoveMiddleShiftsElements() {
        Vector<String> v = abc();
        v.removeElementAt(1);
        check(v.size() == 2, "size after remove middle");
        check("a".equals(v.get(0)), "first element after remove middle");
        check("c".equals(v.get(1)), "second element after remove middle");
    }

    static void testRemoveLastDecrementsSize() {
        Vector<String> v = abc();
        v.removeElementAt(2);
        check(v.size() == 2, "size after remove last");
        check("a".equals(v.get(0)), "first element after remove last");
        check("b".equals(v.get(1)), "second element after remove last");
    }

    static void testIndexEqualsSizeThrows() {
        Vector<String> v = abc();
        try {
            v.removeElementAt(3);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("3 >= 3".equals(e.getMessage()), "message for index == size: " + e.getMessage());
        }
        check(v.size() == 3, "size unchanged after failed remove");
    }

    static void testIndexBeyondSizeThrows() {
        Vector<String> v = abc();
        try {
            v.removeElementAt(10);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("10 >= 3".equals(e.getMessage()), "message for index > size: " + e.getMessage());
        }
        check(v.size() == 3, "size unchanged after out of range remove");
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = abc();
        try {
            v.removeElementAt(-1);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        check(v.size() == 3, "size unchanged after negative index remove");
    }

    static void testEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        try {
            v.removeElementAt(0);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException on empty vector");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        check(v.isEmpty(), "empty vector still empty");
    }

    static void testStructuralModificationInvalidatesIterator() {
        Vector<String> v = abc();
        Iterator<String> it = v.iterator();
        v.removeElementAt(0);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
            // expected
        }
    }
}
