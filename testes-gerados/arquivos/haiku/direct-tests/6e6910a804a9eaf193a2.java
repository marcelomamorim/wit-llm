/*
 * @test
 * @summary Tests Vector iterator remove() behavior: IllegalStateException without next(), removal of last returned element, and ConcurrentModificationException after external structural modification
 * @run main VectorWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveAfterNextRemovesLastReturned();
        testRemoveTwiceWithoutNextThrowsIllegalState();
        testRemoveAllThroughIterator();
        testRemoveAfterExternalModificationThrowsCME();
    }

    static void testRemoveWithoutNextThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException");
        }
        if (v.size() != 2) {
            throw new RuntimeException("Size changed unexpectedly: " + v.size());
        }
    }

    static void testRemoveAfterNextRemovesLastReturned() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        v.add("c");
        Iterator<String> it = v.iterator();
        String first = it.next();
        if (!"a".equals(first)) {
            throw new RuntimeException("Unexpected first element: " + first);
        }
        it.remove();
        if (v.size() != 2) {
            throw new RuntimeException("Expected size 2 but was " + v.size());
        }
        if (!"b".equals(v.get(0)) || !"c".equals(v.get(1))) {
            throw new RuntimeException("Unexpected contents: " + v);
        }
        String second = it.next();
        if (!"b".equals(second)) {
            throw new RuntimeException("Expected b after remove but got " + second);
        }
    }

    static void testRemoveTwiceWithoutNextThrowsIllegalState() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalStateException on second remove");
        }
        if (v.size() != 1) {
            throw new RuntimeException("Expected size 1 but was " + v.size());
        }
    }

    static void testRemoveAllThroughIterator() {
        Vector<Integer> v = new Vector<>();
        for (int i = 0; i < 10; i++) {
            v.add(i);
        }
        Iterator<Integer> it = v.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        if (!v.isEmpty()) {
            throw new RuntimeException("Vector should be empty: " + v);
        }
    }

    static void testRemoveAfterExternalModificationThrowsCME() {
        Vector<String> v = new Vector<>();
        v.add("a");
        v.add("b");
        Iterator<String> it = v.iterator();
        it.next();
        v.add("c");
        boolean thrown = false;
        try {
            it.remove();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected ConcurrentModificationException");
        }
    }
}
