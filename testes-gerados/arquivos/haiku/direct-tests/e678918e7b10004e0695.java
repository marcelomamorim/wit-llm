/*
 * @test
 * @summary Behavioral tests for Vector.setElementAt(E, int) bounds checks and stored values
 * @run main VectorWitupTest
 */

import java.util.Vector;

public class VectorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetWithinBoundsReplacesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.setElementAt("x", 1);
        check("x".equals(v.elementAt(1)), "element at index 1 should be x");
        check("a".equals(v.elementAt(0)), "index 0 should be unchanged");
        check("c".equals(v.elementAt(2)), "index 2 should be unchanged");
        check(v.size() == 3, "size should remain 3");
    }

    static void testSetFirstAndLastElement() {
        Vector<Integer> v = new Vector<>();
        v.add(10);
        v.add(20);
        v.setElementAt(99, 0);
        v.setElementAt(77, 1);
        check(v.elementAt(0) == 99, "first element should be 99");
        check(v.elementAt(1) == 77, "last element should be 77");
    }

    static void testSetNullIsStored() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.setElementAt(null, 0);
        check(v.elementAt(0) == null, "null should be stored");
    }

    static void testSetAtSizeThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        try {
            v.setElementAt("x", 2);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("2 >= 2".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
        check(v.size() == 2, "size should remain 2");
        check("a".equals(v.elementAt(0)) && "b".equals(v.elementAt(1)),
              "contents should be unchanged after failed set");
    }

    static void testSetBeyondSizeThrowsEvenWithCapacity() {
        Vector<String> v = new Vector<>(10);
        v.add("a");
        try {
            v.setElementAt("x", 5);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("5 >= 1".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testSetOnEmptyVectorThrows() {
        Vector<String> v = new Vector<>();
        try {
            v.setElementAt("x", 0);
            throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            check("0 >= 0".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void testNegativeIndexThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        boolean thrown = false;
        try {
            v.setElementAt("x", -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative index should throw ArrayIndexOutOfBoundsException");
        check("a".equals(v.elementAt(0)) && "b".equals(v.elementAt(1)),
              "contents should be unchanged after negative index set");
    }

    public static void main(String[] args) {
        testSetWithinBoundsReplacesElement();
        testSetFirstAndLastElement();
        testSetNullIsStored();
        testSetAtSizeThrows();
        testSetBeyondSizeThrowsEvenWithCapacity();
        testSetOnEmptyVectorThrows();
        testNegativeIndexThrows();
    }
}
