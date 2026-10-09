/*
 * @test
 * @summary Test java.util.concurrent.SynchronousQueue.offer(E e) method
 * @run main SynchronousQueueWitupTest_Pb6d233fe7793
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest_Pb6d233fe7793 {
    public static void main(String[] args) throws Exception {
        testOfferNull();
        testOfferReturnsFalseWhenNoConsumer();
        testOfferReturnsTrueWhenConsumerPresent();
    }

    static void testOfferNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.offer(null);
            throw new Error("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testOfferReturnsFalseWhenNoConsumer() throws Exception {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        // No consumer thread, offer should return false immediately
        boolean offered = queue.offer("test");
        if (offered) {
            throw new Error("offer() returned true but no consumer was waiting");
        }
    }

    static void testOfferReturnsTrueWhenConsumerPresent() throws Exception {
        final SynchronousQueue<String> queue = new SynchronousQueue<>();
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try {
                    String item = queue.take();
                    if (!"test".equals(item)) {
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

        boolean offered = queue.offer("test");
        if (!offered) {
            throw new Error("offer() returned false but consumer was waiting");
        }

        consumer.join();
    }
}
