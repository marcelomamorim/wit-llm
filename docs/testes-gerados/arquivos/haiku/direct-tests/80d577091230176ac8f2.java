/*
 * @test
 * @summary Verifies Iterator.next() behavior of ConcurrentLinkedQueue through its public API
 * @run main ConcurrentLinkedQueueWitupTest
 */
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testEmptyQueueIteratorNextThrows();
        testFifoOrderThroughNext();
        testNextAfterExhaustionThrows();
        testCollectionConstructorOrder();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testEmptyQueueIteratorNextThrows() {
        Iterator<String> it = new ConcurrentLinkedQueue<String>().iterator();
        check(!it.hasNext(), "empty queue iterator must not have next");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException on empty queue");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testFifoOrderThroughNext() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        Iterator<String> it = q.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        check("abc".equals(sb.toString()), "expected FIFO order abc but was " + sb);
    }

    static void testNextAfterExhaustionThrows() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("x");
        Iterator<String> it = q.iterator();
        check("x".equals(it.next()), "first next() must return x");
        try {
            it.next();
            throw new RuntimeException("expected NoSuchElementException after exhaustion");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testCollectionConstructorOrder() {
        ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = q.iterator();
        check(it.next() == 1, "first element must be 1");
        check(it.next() == 2, "second element must be 2");
        check(it.next() == 3, "third element must be 3");
        check(!it.hasNext(), "iterator must be exhausted");
    }
}
