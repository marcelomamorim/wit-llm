/*
 * @test
 * @summary Verifies LinkedBlockingDeque.element() returns the head without removing it and throws NoSuchElementException when empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testEmptyDequeThrows();
        testReturnsHeadWithoutRemoving();
        testHeadAfterAddFirst();
        testHeadFromCollectionConstructor();
        testThrowsAfterDrained();
    }

    static void testEmptyDequeThrows() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        try {
            d.element();
            fail("expected NoSuchElementException on empty deque");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testReturnsHeadWithoutRemoving() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.offerLast("a");
        d.offerLast("b");
        check("a".equals(d.element()), "first element() call");
        check("a".equals(d.element()), "second element() call");
        check(d.size() == 2, "size unchanged after element()");
    }

    static void testHeadAfterAddFirst() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("y");
        d.addFirst("x");
        check("x".equals(d.element()), "head after addFirst");
        check("x".equals(d.removeFirst()), "removeFirst returns same head");
        check("y".equals(d.element()), "new head after removal");
    }

    static void testHeadFromCollectionConstructor() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        check(d.element().equals(1), "head from collection constructor");
        check(d.size() == 3, "size after element() on collection-built deque");
    }

    static void testThrowsAfterDrained() {
        LinkedBlockingDeque<String> d =
            new LinkedBlockingDeque<>(Arrays.asList("p"));
        check("p".equals(d.poll()), "poll returns the only element");
        try {
            d.element();
            fail("expected NoSuchElementException after draining");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    static void fail(String message) {
        throw new RuntimeException(message);
    }
}
