/*
 * @test
 * @summary Verify that the Enumeration returned by Vector.elements() throws NoSuchElementException when exhausted
 * @run main VectorWitupTest
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

public class VectorWitupTest {
    public static void main(String[] args) {
        testEmptyVectorEnumerationThrows();
        testEnumerationThrowsAfterAllElements();
        testRepeatedCallsAfterExhaustionThrow();
        testHasMoreElementsConsistentWithNextElement();
        System.out.println("PASS");
    }

    static void testEmptyVectorEnumerationThrows() {
        Vector<String> v = new Vector<>();
        Enumeration<String> e = v.elements();
        expectNoSuchElement(e);
    }

    static void testEnumerationThrowsAfterAllElements() {
        Vector<String> v = new Vector<>();
        v.addElement("a");
        v.addElement("b");
        v.addElement("c");
        Enumeration<String> e = v.elements();
        check("a".equals(e.nextElement()), "first element");
        check("b".equals(e.nextElement()), "second element");
        check("c".equals(e.nextElement()), "third element");
        expectNoSuchElement(e);
    }

    static void testRepeatedCallsAfterExhaustionThrow() {
        Vector<Integer> v = new Vector<>();
        v.addElement(Integer.valueOf(1));
        Enumeration<Integer> e = v.elements();
        check(e.nextElement().intValue() == 1, "single element");
        expectNoSuchElement(e);
        expectNoSuchElement(e);
        expectNoSuchElement(e);
    }

    static void testHasMoreElementsConsistentWithNextElement() {
        Vector<String> v = new Vector<>();
        v.addElement("x");
        Enumeration<String> e = v.elements();
        check(e.hasMoreElements(), "hasMoreElements before first");
        check("x".equals(e.nextElement()), "element x");
        check(!e.hasMoreElements(), "hasMoreElements after exhaustion");
        expectNoSuchElement(e);
    }

    static void expectNoSuchElement(Enumeration<?> e) {
        try {
            Object o = e.nextElement();
            throw new AssertionError("expected NoSuchElementException, got " + o);
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError("failed: " + what);
        }
    }
}
