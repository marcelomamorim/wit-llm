/*
 * @test
 * @summary Verifies exception behavior of ArrayList iterator remove() (IllegalStateException before next and after double remove, ConcurrentModificationException after structural modification) and normal removal of the last returned element
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveBeforeNextThrowsIllegalStateException();
        testRemoveTwiceThrowsIllegalStateException();
        testRemoveAfterStructuralModificationThrowsConcurrentModificationException();
        testRemoveDeletesLastReturnedElement();
    }

    static void testRemoveBeforeNextThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        Iterator<String> it = list.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException when remove() called before next()");
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new AssertionError("list must be unchanged after failed remove()");
        }
    }

    static void testRemoveTwiceThrowsIllegalStateException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected IllegalStateException on second consecutive remove()");
        }
        if (list.size() != 2 || !"b".equals(list.get(0)) || !"c".equals(list.get(1))) {
            throw new AssertionError("unexpected list state after second remove(): " + list);
        }
    }

    static void testRemoveAfterStructuralModificationThrowsConcurrentModificationException() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        Iterator<String> it = list.iterator();
        it.next();
        list.add("c");
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected ConcurrentModificationException after structural modification");
        }
    }

    static void testRemoveDeletesLastReturnedElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.iterator();
        if (!"a".equals(it.next())) {
            throw new AssertionError("unexpected first element");
        }
        it.remove();
        if (!"b".equals(it.next())) {
            throw new AssertionError("unexpected second element");
        }
        it.remove();
        if (list.size() != 1 || !"c".equals(list.get(0))) {
            throw new AssertionError("unexpected list after removals: " + list);
        }
    }
}
