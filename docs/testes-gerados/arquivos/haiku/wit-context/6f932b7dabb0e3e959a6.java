/*
 * @test
 * @summary Witup-derived tests for SynchronousQueue.put null check, handoff and interrupt behavior
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        for (boolean fair : new boolean[] {false, true}) {
            SynchronousQueue<String> q = new SynchronousQueue<>(fair);

            try {
                q.put(null);
                check(false, "put(null) should throw NullPointerException");
            } catch (NullPointerException expected) {
            }
            check(q.poll() == null, "queue should remain empty after rejected null put");

            final SynchronousQueue<String> hand = new SynchronousQueue<>(fair);
            final String[] received = new String[1];
            Thread consumer = new Thread(() -> {
                try {
                    received[0] = hand.take();
                } catch (InterruptedException e) {
                }
            });
            consumer.start();
            hand.put("x");
            consumer.join();
            check("x".equals(received[0]), "consumer should receive the element handed off by put");
        }

        SynchronousQueue<String> q = new SynchronousQueue<>();
        Thread.currentThread().interrupt();
        try {
            q.put("y");
            check(false, "put should throw InterruptedException when interrupted with no consumer");
        } catch (InterruptedException expected) {
        }
        check(!Thread.interrupted(), "interrupt status should be cleared after put throws InterruptedException");
    }
}
