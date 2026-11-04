/*
 * @test
 * @summary Verifies observable behavior of LinkedTransferQueue.put(E) through public API
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        testPutSingleElement();
        testPutPreservesFifoOrder();
        testPutAfterConstructorWithCollection();
        testPutDeliversToWaitingConsumer();
    }

    static void testPutSingleElement() throws Exception {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        check(q.isEmpty(), "new queue should be empty");
        q.put("a");
        check(!q.isEmpty(), "queue should not be empty after put");
        check(q.size() == 1, "size should be 1 after one put, was " + q.size());
        check("a".equals(q.peek()), "peek should return put element");
        check("a".equals(q.take()), "take should return put element");
        check(q.isEmpty(), "queue should be empty after take");
    }

    static void testPutPreservesFifoOrder() {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();
        for (int i = 0; i < 100; i++) {
            q.put(i);
        }
        check(q.size() == 100, "size should be 100, was " + q.size());
        for (int i = 0; i < 100; i++) {
            Integer v = q.poll();
            check(v != null && v == i, "expected " + i + " but got " + v);
        }
        check(q.poll() == null, "queue should be empty after draining");
    }

    static void testPutAfterConstructorWithCollection() {
        LinkedTransferQueue<String> q = new LinkedTransferQueue<>(Arrays.asList("p", "q"));
        q.put("r");
        check(q.size() == 3, "size should be 3, was " + q.size());
        check("p".equals(q.poll()), "first element should be p");
        check("q".equals(q.poll()), "second element should be q");
        check("r".equals(q.poll()), "third element should be r");
        check(q.isEmpty(), "queue should be empty");
    }

    static void testPutDeliversToWaitingConsumer() throws Exception {
        final LinkedTransferQueue<String> q = new LinkedTransferQueue<>();
        final Object[] result = new Object[1];
        Thread consumer = new Thread(() -> {
            try {
                result[0] = q.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        q.put("x");
        consumer.join(10000);
        check(!consumer.isAlive(), "consumer should finish after put");
        check("x".equals(result[0]), "consumer should receive \"x\", got " + result[0]);
        check(q.isEmpty(), "queue should be empty after handoff");
    }
}
