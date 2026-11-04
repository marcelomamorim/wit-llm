/*
 * @test
 * @summary Verifies LinkedBlockingQueue.take() FIFO retrieval, blocking until an element is available, and InterruptedException when the calling thread is interrupted
 * @run main LinkedBlockingQueueWitupTest
 */
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeReturnsHeadFifo();
        testTakeWaitsForProducer();
        testTakeInterruptedThrowsAndKeepsElement();
        System.out.println("PASSED");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testTakeReturnsHeadFifo() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.offer("a");
        q.offer("b");
        try {
            check("a".equals(q.take()), "take() should return head element first");
            check("b".equals(q.take()), "take() should return second element next");
        } catch (InterruptedException e) {
            throw new RuntimeException("Unexpected InterruptedException", e);
        }
        check(q.isEmpty(), "queue should be empty after taking all elements");
    }

    static void testTakeWaitsForProducer() throws Exception {
        final LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        final int[] result = new int[1];
        final Throwable[] error = new Throwable[1];
        Thread consumer = new Thread(() -> {
            try {
                result[0] = q.take();
            } catch (Throwable t) {
                error[0] = t;
            }
        });
        consumer.start();
        q.put(42);
        consumer.join(10000);
        check(!consumer.isAlive(), "consumer thread should finish after an element is put");
        check(error[0] == null, "take() should not fail: " + error[0]);
        check(result[0] == 42, "take() should return the produced element");
    }

    static void testTakeInterruptedThrowsAndKeepsElement() {
        LinkedBlockingQueue<String> q = new LinkedBlockingQueue<>();
        q.offer("x");
        Thread.currentThread().interrupt();
        boolean caught = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            caught = true;
        }
        check(caught, "take() should throw InterruptedException when the thread is interrupted");
        check(!Thread.currentThread().isInterrupted(), "interrupt status should be cleared after InterruptedException");
        check(q.size() == 1, "interrupted take() must not remove the element");
    }
}
