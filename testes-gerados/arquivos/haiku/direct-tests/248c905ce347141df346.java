/*
 * @test
 * @summary Verifies ArrayBlockingQueue iterator next() returns elements in FIFO order and throws NoSuchElementException when exhausted
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testFifoOrderAndExhaustion() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(3);
        q.offer(1);
        q.offer(2);
        q.offer(3);
        Iterator<Integer> it = q.iterator();
        check(it.hasNext(), "iterator should have next on non-empty queue");
        check(it.next() == 1, "first next() should return 1");
        check(it.next() == 2, "second next() should return 2");
        check(it.next() == 3, "third next() should return 3");
        check(!it.hasNext(), "hasNext() should be false after last element");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() past end should throw NoSuchElementException");
    }

    static void testEmptyQueueNextThrows() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(2);
        Iterator<String> it = q.iterator();
        check(!it.hasNext(), "empty queue iterator should have no next");
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() on empty queue iterator should throw NoSuchElementException");
    }

    static void testFairConstructorWithCollectionOrder() {
        List<String> src = Arrays.asList("a", "b", "c");
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(5, true, src);
        List<String> seen = new ArrayList<>();
        Iterator<String> it = q.iterator();
        while (it.hasNext()) {
            seen.add(it.next());
        }
        check(seen.equals(src), "iteration order should match insertion order, got " + seen);
    }

    static void testSingleElement() {
        ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<>(1);
        q.offer(42);
        Iterator<Integer> it = q.iterator();
        check(it.next() == 42, "single element next() should return 42");
        check(!it.hasNext(), "no next after single element");
    }

    public static void main(String[] args) {
        testFifoOrderAndExhaustion();
        testEmptyQueueNextThrows();
        testFairConstructorWithCollectionOrder();
        testSingleElement();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
