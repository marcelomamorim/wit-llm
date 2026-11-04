/*
 * @test
 * @summary ArrayList ListIterator.set(E) behavior: state check, element update, and comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSetBeforeNextThrowsIllegalState() {
        List<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set() without prior next() must throw IllegalStateException");
        check("a".equals(list.get(0)), "list must be unchanged");
    }

    static void testSetAfterNextUpdatesElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set("x");
        check("x".equals(list.get(0)), "set() after next() must replace the returned element");
        check("b".equals(list.get(1)), "other elements must be unchanged");
        check(list.size() == 2, "set() must not change size");
    }

    static void testSetAfterPreviousUpdatesElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator(2);
        it.previous();
        it.set("y");
        check("a".equals(list.get(0)), "first element must be unchanged");
        check("y".equals(list.get(1)), "set() after previous() must replace the returned element");
    }

    static void testSetAllowsNullElement() {
        List<String> list = new ArrayList<>();
        list.add("a");
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set(null);
        check(list.get(0) == null, "set(null) must store null");
    }

    static void testStructuralModificationDetected() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ListIterator<String> it = list.listIterator();
        it.next();
        list.add("c");
        boolean thrown = false;
        try {
            it.set("x");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "set() after external structural modification must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testSetBeforeNextThrowsIllegalState();
        testSetAfterNextUpdatesElement();
        testSetAfterPreviousUpdatesElement();
        testSetAllowsNullElement();
        testStructuralModificationDetected();
    }
}
