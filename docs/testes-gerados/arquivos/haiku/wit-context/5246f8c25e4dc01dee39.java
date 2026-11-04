/*
 * @test
 * @summary Checks that Vector's ListIterator.set(E) throws IllegalStateException when no valid last-returned element exists
 * @run main VectorWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testSetWithoutNextThrows();
        testSetAfterNextUpdatesElement();
        testSetAfterRemoveThrows();
        testSetAfterAddThrows();
        testSetAfterPreviousUpdatesElement();
    }

    static void testSetWithoutNextThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when set() called before next()");
        }
        if (!v.get(0).equals("a") || !v.get(1).equals("b")) {
            throw new RuntimeException("Vector modified by failed set()");
        }
    }

    static void testSetAfterNextUpdatesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        it.next();
        it.set("x");
        if (!"x".equals(v.get(0))) {
            throw new RuntimeException("set() after next() did not update element: " + v.get(0));
        }
    }

    static void testSetAfterRemoveThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when set() called after remove()");
        }
    }

    static void testSetAfterAddThrows() {
        Vector<String> v = new Vector<>();
        v.add("a");
        ListIterator<String> it = v.listIterator();
        it.next();
        it.add("n");
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException when set() called after add()");
        }
    }

    static void testSetAfterPreviousUpdatesElement() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        ListIterator<String> it = v.listIterator(2);
        it.previous();
        it.set("y");
        if (!"y".equals(v.get(1))) {
            throw new RuntimeException("set() after previous() did not update element: " + v.get(1));
        }
    }
}
