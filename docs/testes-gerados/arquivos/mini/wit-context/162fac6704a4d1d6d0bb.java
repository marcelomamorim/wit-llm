/*
 * @test
 * @summary Test deterministic behavior of SynchronousQueue.offer(E) including null rejection
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {
    public static void main(String[] args) throws Exception {
        testOfferRejectsNull();
        testOfferReturnsFalseWhenNoConsumer();
        testOfferSucceedsWithConsumer();
    }

    static void testOfferRejectsNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.offer(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferReturnsFalseWhenNoConsumer() throws Exception {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        // offer returns false immediately if no consumer is waiting
        boolean offered = queue.offer("test");
        if (offered) {
            throw new Error("offer should return false when no consumer is waiting");
        }
    }

    static void testOfferSucceedsWithConsumer() throws Exception {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = queue.take();
                    if (!"hello".equals(item)) {
                        throw new Error("Consumer received wrong item: " + item);
                    }
                } catch (InterruptedException e) {
                    throw new Error("Consumer interrupted", e);
                }
            }
        });
        consumer.start();
        // Give consumer a moment to start and wait
        Thread.sleep(100);
        boolean offered = queue.offer("hello");
        if (!offered) {
            throw new Error("offer should return true when consumer is waiting");
        }
        consumer.join();
    }
}
