/*
 * @test
 * @summary ArrayList.SubList iterator next() observable behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSubListIteratorReturnsElementsInOrder();
        testEmptySubListNextThrows();
        testNextPastEndThrows();
        testStructuralModificationThrowsCME();
    }

    static void testSubListIteratorReturnsElementsInOrder() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        Iterator<Integer> it = list.subList(1, 4).iterator();
        check(it.hasNext(), "hasNext should be true");
        check(it.next() == 1, "first element should be 1");
        check(it.next() == 2, "second element should be 2");
        check(it.next() == 3, "third element should be 3");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testEmptySubListNextThrows() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Iterator<Integer> it = list.subList(0, 0).iterator();
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testNextPastEndThrows() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        Iterator<Integer> it = list.subList(0, 2).iterator();
        check(it.next() == 10, "first element should be 10");
        check(it.next() == 20, "second element should be 20");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testStructuralModificationThrowsCME() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.subList(0, 2).iterator();
        list.add(4);
        try {
            it.next();
            throw new RuntimeException("expected ConcurrentModificationException");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
