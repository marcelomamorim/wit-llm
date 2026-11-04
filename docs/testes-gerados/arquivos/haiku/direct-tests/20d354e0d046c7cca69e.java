/*
 * @test
 * @summary Verifies CopyOnWriteArrayList iterator next() behavior through public APIs
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsElementsInOrder() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(new String[]{"a", "b", "c"});
        Iterator<String> it = list.iterator();
        check(it.hasNext(), "hasNext should be true for non-empty list");
        check("a".equals(it.next()), "first next() should return a");
        check("b".equals(it.next()), "second next() should return b");
        check("c".equals(it.next()), "third next() should return c");
        check(!it.hasNext(), "hasNext should be false after consuming all elements");
    }

    static void testNextThrowsWhenExhausted() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(new String[]{"x"});
        Iterator<String> it = list.iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past end should throw NoSuchElementException");
    }

    static void testNextOnEmptyListThrows() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
        Iterator<Integer> it = list.iterator();
        check(!it.hasNext(), "empty list iterator should have no next");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty list iterator should throw NoSuchElementException");
    }

    static void testNextUsesSnapshotIgnoringLaterModifications() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(new String[]{"a", "b"});
        Iterator<String> it = list.iterator();
        list.add("c");
        list.set(0, "z");
        check("a".equals(it.next()), "snapshot iterator should return original first element");
        check("b".equals(it.next()), "snapshot iterator should return original second element");
        check(!it.hasNext(), "snapshot iterator should not reflect added element");
    }

    static void testNextReturnsNullElements() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(new String[]{null, "x"});
        Iterator<String> it = list.iterator();
        check(it.next() == null, "next() should return null element");
        check("x".equals(it.next()), "next() should return following non-null element");
    }

    static void testNextFromCollectionConstructor() {
        Collection<Integer> source = Arrays.asList(1, 2, 3);
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>(source);
        Iterator<Integer> it = list.iterator();
        int sum = 0;
        while (it.hasNext()) {
            sum += it.next();
        }
        check(sum == 6, "sum of iterated elements should be 6 but was " + sum);
    }

    public static void main(String[] args) {
        testNextReturnsElementsInOrder();
        testNextThrowsWhenExhausted();
        testNextOnEmptyListThrows();
        testNextUsesSnapshotIgnoringLaterModifications();
        testNextReturnsNullElements();
        testNextFromCollectionConstructor();
    }
}
