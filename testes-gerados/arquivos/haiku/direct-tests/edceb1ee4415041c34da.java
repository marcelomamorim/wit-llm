/*
 * @test
 * @summary Verifies LinkedBlockingDeque.getLast() returns the tail element without removing it and throws NoSuchElementException when empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testGetLastEmptyThrows();
        testGetLastReturnsTailWithoutRemoving();
        testGetLastAfterAddFirstSingleElement();
        testGetLastAfterPollLast();
        testGetLastFromCollectionConstructor();
        testGetLastBoundedCapacity();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetLastEmptyThrows() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        try {
            d.getLast();
            throw new RuntimeException("expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testGetLastReturnsTailWithoutRemoving() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addFirst("a");
        d.addLast("b");
        check("b".equals(d.getLast()), "getLast should return last element");
        check(d.size() == 2, "getLast must not remove the element");
        check("b".equals(d.getLast()), "second getLast should still return last element");
    }

    static void testGetLastAfterAddFirstSingleElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addFirst("only");
        check("only".equals(d.getLast()), "single element should be returned by getLast");
    }

    static void testGetLastAfterPollLast() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("x");
        d.addLast("y");
        d.addLast("z");
        d.pollLast();
        check("y".equals(d.getLast()), "getLast should reflect new tail after pollLast");
    }

    static void testGetLastFromCollectionConstructor() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        check(d.getLast().intValue() == 3, "getLast should return last element of source collection");
    }

    static void testGetLastBoundedCapacity() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        d.addLast("only");
        check("only".equals(d.getLast()), "getLast should work on bounded deque");
        d.pollLast();
        try {
            d.getLast();
            throw new RuntimeException("expected NoSuchElementException after draining bounded deque");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }
}
