/*
 * @test
 * @summary Witup-derived checks for ConcurrentLinkedQueue iterator next() exhaustion behavior
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {

    public static void main(String[] args) {
        emptyQueueIteratorNextThrows();
        singleElementExhaustionThrows();
        fifoOrderThenExhaustionThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void emptyQueueIteratorNextThrows() {
        Iterator<String> it = new ConcurrentLinkedQueue<String>().iterator();
        check(!it.hasNext(), "empty queue iterator must not have next");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty iterator");
        } catch (NoSuchElementException expected) {
        }
    }

    static void singleElementExhaustionThrows() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        queue.add("a");
        Iterator<String> it = queue.iterator();
        check(it.hasNext(), "iterator must have one element");
        check("a".equals(it.next()), "next must return the single element");
        check(!it.hasNext(), "iterator must be exhausted");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
        }
    }

    static void fifoOrderThenExhaustionThrows() {
        ConcurrentLinkedQueue<String> queue =
            new ConcurrentLinkedQueue<>(Arrays.asList("x", "y"));
        Iterator<String> it = queue.iterator();
        check("x".equals(it.next()), "first next must be x");
        check("y".equals(it.next()), "second next must be y");
        check(!it.hasNext(), "iterator must be exhausted after two elements");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException after FIFO traversal");
        } catch (NoSuchElementException expected) {
        }
    }
}
