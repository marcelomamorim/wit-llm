/*
 * @test
 * @summary Tests Vector.add(int, E) insertion behavior and index validation
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testAppendAtSize();
        testInsertAtZeroOnEmpty();
        testInsertInMiddleShiftsElements();
        testIndexGreaterThanSizeThrows();
        testNegativeIndexThrows();
        testNullElementIsStored();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendAtSize() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add(1, "b");
        check(v.size() == 2, "size after append at size index");
        check("a".equals(v.get(0)), "element 0 after append");
        check("b".equals(v.get(1)), "element 1 after append");
    }

    static void testInsertAtZeroOnEmpty() {
        Vector<String> v = new Vector<>();
        v.add(0, "x");
        check(v.size() == 1, "size after insert at 0 on empty");
        check("x".equals(v.get(0)), "element after insert at 0 on empty");
    }

    static void testInsertInMiddleShiftsElements() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("c");
        v.add(1, "b");
        check(v.size() == 3, "size after middle insert");
        check("a".equals(v.get(0)), "element 0 after middle insert");
        check("b".equals(v.get(1)), "element 1 after middle insert");
        check("c".equals(v.get(2)), "element 2 after middle insert");
    }

    static void testIndexGreaterThanSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        boolean thrown = false;
        try {
            v.add(2, "b");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for index > size");
        check(v.size() == 1, "vector must be unchanged after failed insert");
        check("a".equals(v.get(0)), "element must be unchanged after failed insert");
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        boolean thrown = false;
        try {
            v.add(-1, "x");
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected ArrayIndexOutOfBoundsException for negative index");
        check(v.size() == 0, "vector must be unchanged after negative index insert");
    }

    static void testNullElementIsStored() {
        Vector<String> v = new Vector<>();
        v.add(0, null);
        check(v.size() == 1, "size after inserting null");
        check(v.get(0) == null, "null element must be stored");
    }
}
