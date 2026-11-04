/*
 * @test
 * @summary Verifies next() of iterators obtained from CopyOnWriteArrayList via public API: ordered traversal, snapshot semantics, and NoSuchElementException when exhausted.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void testIteratorNextOrderAndExhaustion() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(new String[]{"a", "b"});
        Iterator<String> it = list.iterator();
        check(it.hasNext(), "hasNext should be true initially");
        check("a".equals(it.next()), "first next() should return a");
        check("b".equals(it.next()), "second next() should return b");
        check(!it.hasNext(), "hasNext should be false after all elements");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past end should throw NoSuchElementException");
    }

    static void testEmptyListNextThrows() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
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

    static void testSnapshotNextIgnoresLaterMutation() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2});
        Iterator<Integer> it = list.iterator();
        list.add(3);
        check(it.next() == 1, "snapshot iterator first element should be 1");
        check(it.next() == 2, "snapshot iterator second element should be 2");
        check(!it.hasNext(), "snapshot iterator should not reflect added element");
        check(list.size() == 3, "list should contain 3 elements after add");
    }

    static void testSubListIteratorNext() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
        List<Integer> sub = list.subList(1, 3);
        Iterator<Integer> it = sub.iterator();
        check(it.next() == 2, "sublist iterator first element should be 2");
        check(it.next() == 3, "sublist iterator second element should be 3");
        check(!it.hasNext(), "sublist iterator should be exhausted");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "sublist iterator next() past end should throw NoSuchElementException");
    }

    public static void main(String[] args) {
        testIteratorNextOrderAndExhaustion();
        testEmptyListNextThrows();
        testSnapshotNextIgnoresLaterMutation();
        testSubListIteratorNext();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
