/* @test    @summary Test DelayQueue.next() method for correct element retrieval and exception handling    @run main DelayQueueWitupTest */

import java.util.concurrent.DelayQueue;
import java.util.NoSuchElementException;

public class DelayQueueWitupTest {

    public static void main(String[] args) {
        testNextReturnsElement();
        testNextThrowsExceptionWhenEmpty();
    }

    private static void testNextReturnsElement() {
        DelayQueue<Integer> dq = new DelayQueue<>();
        dq.offer(10);
        dq.offer(20);
        Integer first = dq.next();
        if (first == null || first != 10) {
            throw new AssertionError("Expected first element to be 10, but got: " + first);
        }
    }

    private static void testNextThrowsExceptionWhenEmpty() {
        DelayQueue<Integer> dq = new DelayQueue<>();
        boolean exceptionThrown = false;
        try {
            dq.next();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NoSuchElementException when calling next() on empty DelayQueue");
        }
    }
}
