/*
 * @test
 * @summary Verifies Vector enumeration nextElement() ordering and NoSuchElementException behavior
 * @run main VectorWitupTest
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testEmptyVectorThrows();
        testSequentialElementsInOrder();
        testRepeatedCallAfterExhaustionThrows();
        testSmallCapacityIncrementVector();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyVectorThrows() {
        Enumeration<String> e = new Vector<String>().elements();
        check(!e.hasMoreElements(), "empty enumeration should have no elements");
        try {
            e.nextElement();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            check("Vector Enumeration".equals(ex.getMessage()),
                  "unexpected message: " + ex.getMessage());
        }
    }

    static void testSequentialElementsInOrder() {
        Vector<Integer> v = new Vector<>();
        v.add(1);
        v.add(2);
        v.add(3);
        Enumeration<Integer> e = v.elements();
        check(e.nextElement() == 1, "first element should be 1");
        check(e.nextElement() == 2, "second element should be 2");
        check(e.nextElement() == 3, "third element should be 3");
        check(!e.hasMoreElements(), "enumeration should be exhausted");
    }

    static void testRepeatedCallAfterExhaustionThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        Enumeration<String> e = v.elements();
        check("a".equals(e.nextElement()), "expected a");
        for (int i = 0; i < 2; i++) {
            try {
                e.nextElement();
                throw new RuntimeException("expected NoSuchElementException on call " + i);
            } catch (NoSuchElementException ex) {
                check("Vector Enumeration".equals(ex.getMessage()),
                      "unexpected message: " + ex.getMessage());
            }
        }
    }

    static void testSmallCapacityIncrementVector() {
        Vector<String> v = new Vector<>(0, 1);
        for (int i = 0; i < 5; i++) {
            v.add("e" + i);
        }
        Enumeration<String> e = v.elements();
        for (int i = 0; i < 5; i++) {
            check(e.hasMoreElements(), "missing element " + i);
            check(("e" + i).equals(e.nextElement()), "wrong element at " + i);
        }
        check(!e.hasMoreElements(), "enumeration should be exhausted");
    }
}
