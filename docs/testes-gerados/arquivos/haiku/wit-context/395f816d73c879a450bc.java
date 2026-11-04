/*
 * @test
 * @summary Witup-derived behavioral tests for LinkedBlockingDeque.element()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        emptyDequeThrowsNoSuchElement();
        returnsHeadWithoutRemoving();
        throwsAfterDrainingToEmpty();
        worksOnDequeBuiltFromCollection();
    }

    static void emptyDequeThrowsNoSuchElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            d.element();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "element() on empty deque must throw NoSuchElementException");
        check(d.size() == 0, "deque must remain empty");
    }

    static void returnsHeadWithoutRemoving() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.offer("a");
        d.offer("b");
        check("a".equals(d.element()), "element() must return the head");
        check("a".equals(d.element()), "element() must not remove the head");
        check(d.size() == 2, "size must be unchanged by element()");
        check("a".equals(d.poll()), "poll() must still return the head");
    }

    static void throwsAfterDrainingToEmpty() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(4);
        d.offer("x");
        check("x".equals(d.poll()), "poll() must return the element");
        boolean thrown = false;
        try {
            d.element();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "element() after draining must throw NoSuchElementException");
    }

    static void worksOnDequeBuiltFromCollection() {
        LinkedBlockingDeque<String> d =
            new LinkedBlockingDeque<>(Arrays.asList("p", "q"));
        check("p".equals(d.element()), "element() must return first collection element");
        d.clear();
        boolean thrown = false;
        try {
            d.element();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "element() after clear() must throw NoSuchElementException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
