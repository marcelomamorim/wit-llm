/*
 * @test
 * @summary Witup-derived behavioral checks for ArrayList.ListItr.set(E) via public listIterator()
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;

public class ArrayListWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSetWithoutNextThrowsISE() {
        List<String> list = new ArrayList<>();
        ListIterator<String> it = list.listIterator();
        boolean thrown = false;
        try {
            it.set("x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set() before next() must throw IllegalStateException");
    }

    static void testSetAfterRemoveThrowsISE() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        ListIterator<String> it = list.listIterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.set("z");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "set() after remove() must throw IllegalStateException");
    }

    static void testSetAfterNextUpdatesElement() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        ListIterator<String> it = list.listIterator();
        it.next();
        it.set("z");
        check("z".equals(list.get(0)), "set() after next() must replace the last returned element");
        check(list.size() == 2, "set() must not change list size");
    }

    static void testSetAfterStructuralModificationThrowsCME() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        ListIterator<String> it = list.listIterator();
        it.next();
        list.add("c");
        boolean thrown = false;
        try {
            it.set("z");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "set() after external structural modification must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testSetWithoutNextThrowsISE();
        testSetAfterRemoveThrowsISE();
        testSetAfterNextUpdatesElement();
        testSetAfterStructuralModificationThrowsCME();
    }
}
