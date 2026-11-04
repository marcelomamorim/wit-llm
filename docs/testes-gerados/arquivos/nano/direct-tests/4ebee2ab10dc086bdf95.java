/* @test    @summary Test for PriorityBlockingQueue.next() method behavior.    @run main PriorityBlockingQueueWitupTest */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.NoSuchElementException;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsExceptionWhenEmpty();
    }

    static void testNextReturnsElement() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        pq.add(10);
        pq.add(20);
        pq.add(15);
        Integer first = pq.next();
        assert first != null : "Expected non-null element";
        assert first.equals(10) : "Expected first element to be 10";
    }

    static void testNextThrowsExceptionWhenEmpty() {
        PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<>();
        boolean thrown = false;
        try {
            pq.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assert thrown : "Expected NoSuchElementException when calling next() on empty queue";
    }
}
