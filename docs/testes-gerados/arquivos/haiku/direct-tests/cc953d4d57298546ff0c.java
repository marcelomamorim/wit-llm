/*
 * @test
 * @summary Verify observable behavior of PriorityBlockingQueue.take(): priority order, blocking until an element is offered, and InterruptedException handling.
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNaturalOrderTake();
        testReverseComparatorTake();
        testTakeBlocksUntilOffer();
        testInterruptWhileWaiting();
        testPreInterruptedTakeThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNaturalOrderTake() throws InterruptedException {
        PriorityBlockingQueue<Integer> q = new PriorityBlockingQueue<>();
        q.offer(3);
        q.offer(1);
        q.offer(2);
        check(q.take() == 1, "expected smallest element first");
        check(q.take() == 2, "expected second smallest element");
        check(q.take() == 3, "expected largest element last");
        check(q.isEmpty(), "queue should be empty after taking all elements");
    }

    static void testReverseComparatorTake() throws InterruptedException {
        PriorityBlockingQueue<Integer> q =
            new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        q.offer(1);
        q.offer(3);
        q.offer(2);
        check(q.take() == 3, "reverse comparator should yield max first");
        check(q.take() == 2, "reverse comparator should yield 2 second");
        check(q.take() == 1, "reverse comparator should yield min last");
    }

    static void testTakeBlocksUntilOffer() throws InterruptedException {
        final PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        final String[] result = new String[1];
        Thread consumer = new Thread(() -> {
            try {
                result[0] = q.take();
            } catch (InterruptedException e) {
                // result stays null; checked below
            }
        });
        consumer.start();
        q.offer("x");
        consumer.join(10000);
        check(!consumer.isAlive(), "take() should return after an element is offered");
        check("x".equals(result[0]), "take() should return the offered element");
    }

    static void testInterruptWhileWaiting() throws InterruptedException {
        final PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        final boolean[] interrupted = new boolean[1];
        Thread consumer = new Thread(() -> {
            try {
                q.take();
            } catch (InterruptedException e) {
                interrupted[0] = true;
            }
        });
        consumer.start();
        Thread.sleep(100);
        consumer.interrupt();
        consumer.join(10000);
        check(!consumer.isAlive(), "interrupted take() should terminate");
        check(interrupted[0], "take() should throw InterruptedException when interrupted while waiting");
        check(q.isEmpty(), "queue should remain empty");
    }

    static void testPreInterruptedTakeThrows() {
        PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
        Thread.currentThread().interrupt();
        boolean threw = false;
        try {
            q.take();
        } catch (InterruptedException e) {
            threw = true;
        } finally {
            Thread.interrupted();
        }
        check(threw, "take() should throw InterruptedException when the thread is already interrupted");
    }
}
