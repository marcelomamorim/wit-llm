/*
 * @test
 * @summary Behavioral tests for LinkedBlockingDeque.put (appends at tail, blocks when full, responds to interrupt)
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void awaitBlocked(Thread t) throws InterruptedException {
        while (t.isAlive()
               && t.getState() != Thread.State.WAITING
               && t.getState() != Thread.State.TIMED_WAITING) {
            Thread.sleep(1);
        }
        check(t.isAlive(), "thread terminated before blocking");
    }

    static void testUnboundedAppendsInOrder() throws InterruptedException {
        LinkedBlockingDeque<String> q = new LinkedBlockingDeque<>();
        q.put("a");
        q.put("b");
        q.put("c");
        check(q.size() == 3, "size after three puts: " + q.size());
        check("c".equals(q.peekLast()), "peekLast should be last put element");
        check("a".equals(q.peekFirst()), "peekFirst should be first put element");
        check("a".equals(q.take()), "take should return first element (FIFO)");
        check("b".equals(q.take()), "second take order");
        check("c".equals(q.take()), "third take order");
        check(q.isEmpty(), "deque should be empty");
    }

    static void testBoundedPutBlocksUntilSpace() throws InterruptedException {
        final LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>(1);
        q.put(1);
        check(q.remainingCapacity() == 0, "deque should be full");

        Thread producer = new Thread(() -> {
            try {
                q.put(2);
            } catch (InterruptedException e) {
                throw new RuntimeException("unexpected interrupt", e);
            }
        });
        producer.start();
        awaitBlocked(producer);
        check(q.size() == 1, "put must not add when full while blocked");

        check(q.take() == 1, "take should free space and return 1");
        producer.join();
        check(!producer.isAlive(), "producer should complete after space freed");
        check(q.size() == 1, "size after unblocked put: " + q.size());
        check(q.peek() == 2, "unblocked put should insert 2");
    }

    static void testPutInterruptedWhenFull() throws InterruptedException {
        final LinkedBlockingDeque<Integer> q = new LinkedBlockingDeque<>(1);
        q.put(1);
        final boolean[] interrupted = new boolean[1];

        Thread producer = new Thread(() -> {
            try {
                q.put(2);
            } catch (InterruptedException e) {
                interrupted[0] = true;
            }
        });
        producer.start();
        awaitBlocked(producer);
        producer.interrupt();
        producer.join();

        check(interrupted[0], "put on full deque should throw InterruptedException when interrupted");
        check(q.size() == 1, "interrupted put must not insert element");
        check(q.peek() == 1, "existing element must be preserved");
    }

    public static void main(String[] args) throws Exception {
        testUnboundedAppendsInOrder();
        testBoundedPutBlocksUntilSpace();
        testPutInterruptedWhenFull();
    }
}
