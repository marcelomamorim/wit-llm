/*
 * @test
 * @summary Verify Vector.lastElement returns the last element or throws NoSuchElementException when empty
 * @run main VectorWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyDefaultThrows();
        testEmptyCapacityConstructorThrows();
        testEmptyCapacityIncrementConstructorThrows();
        testSingleElement();
        testReturnsLastAfterAdds();
        testAfterRemoveLast();
        testNullLastElement();
        testFromCollection();
        testEmptyAfterRemovingAll();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyDefaultThrows() {
        Vector<String> v = new Vector<>();
        try {
            v.lastElement();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testEmptyCapacityConstructorThrows() {
        Vector<String> v = new Vector<>(10);
        try {
            v.lastElement();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testEmptyCapacityIncrementConstructorThrows() {
        Vector<String> v = new Vector<>(10, 5);
        try {
            v.lastElement();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testSingleElement() {
        Vector<String> v = new Vector<>();
        v.add("only");
        check("only".equals(v.lastElement()), "single element should be last");
    }

    static void testReturnsLastAfterAdds() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        check("c".equals(v.lastElement()), "last element should be c");
        check(v.size() == 3, "lastElement must not change size");
    }

    static void testAfterRemoveLast() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        v.remove(2);
        check("b".equals(v.lastElement()), "after removing last, new last should be b");
    }

    static void testNullLastElement() {
        Vector<String> v = new Vector<>();
        v.add("x");
        v.add(null);
        check(v.lastElement() == null, "null last element should be returned as null");
    }

    static void testFromCollection() {
        Vector<Integer> v = new Vector<>(Arrays.asList(1, 2, 3));
        check(v.lastElement().intValue() == 3, "last element of collection-based vector should be 3");
    }

    static void testEmptyAfterRemovingAll() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.remove(0);
        try {
            v.lastElement();
            throw new RuntimeException("expected NoSuchElementException after removing all");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
