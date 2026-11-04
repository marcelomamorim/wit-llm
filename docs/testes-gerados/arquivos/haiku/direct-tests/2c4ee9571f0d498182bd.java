/*
 * @test
 * @summary Verifies observable behavior of LinkedList ListItr.next() through public iterators
 * @run main LinkedListWitupTest
 */
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListWitupTest {
    public static void main(String[] args) {
        testOrderAndExhaustion();
        testEmptyNextThrows();
        testNextAfterExhaustionThrows();
        testConcurrentModificationDetected();
        testNullElementReturned();
        testNextAfterRemove();
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }

    static void testOrderAndExhaustion() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Iterator<String> it = list.iterator();
        check(it.next().equals("a"), "first element");
        check(it.next().equals("b"), "second element");
        check(it.next().equals("c"), "third element");
        check(!it.hasNext(), "iterator exhausted");
    }

    static void testEmptyNextThrows() {
        Iterator<Integer> it = new LinkedList<Integer>().iterator();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty list must throw NoSuchElementException");
    }

    static void testNextAfterExhaustionThrows() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "single element");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion must throw NoSuchElementException");
    }

    static void testConcurrentModificationDetected() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "first element before modification");
        list.add(3);
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after structural modification must throw ConcurrentModificationException");
    }

    static void testNullElementReturned() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        list.add("x");
        ListIterator<String> li = list.listIterator();
        check(li.next() == null, "null element must be returned");
        check("x".equals(li.next()), "non-null element after null");
    }

    static void testNextAfterRemove() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> it = list.iterator();
        check(it.next() == 1, "first element");
        it.remove();
        check(it.next() == 2, "next after remove must return following element");
        check(list.size() == 2, "size after remove");
        check(list.get(0) == 2 && list.get(1) == 3, "remaining contents");
    }
}
