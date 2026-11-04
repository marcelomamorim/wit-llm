/*
 * @test
 * @summary Checks SynchronousQueue.offer(E) null rejection, non-blocking failure without a consumer, and handoff to a waiting consumer
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE(new SynchronousQueue<String>());
        testNullThrowsNPE(new SynchronousQueue<String>(true));
        testOfferWithoutConsumerReturnsFalse(new SynchronousQueue<String>());
        testOfferWithoutConsumerReturnsFalse(new SynchronousQueue<String>(true));
        testOfferHandsOffToConsumer(new SynchronousQueue<String>());
        testOfferHandsOffToConsumer(new SynchronousQueue<String>(true));
    }

    static void testNullThrowsNPE(SynchronousQueue<String> q) {
        boolean thrown = false;
        try {
            q.offer(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("offer(null) must throw NullPointerException");
        }
        if (q.poll() != null) {
            throw new RuntimeException("queue must remain empty after rejected null offer");
        }
    }

    static void testOfferWithoutConsumerReturnsFalse(SynchronousQueue<String> q) {
        if (q.offer("x")) {
            throw new RuntimeException("offer without waiting consumer must return false");
        }
        if (q.poll() != null) {
            throw new RuntimeException("no element should be available after failed offer");
        }
    }

    static void testOfferHandsOffToConsumer(final SynchronousQueue<String> q) throws InterruptedException {
        final String[] got = new String[1];
        Thread consumer = new Thread(() -> {
            try {
                got[0] = q.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        boolean ok = false;
        for (int i = 0; i < 1000000 && !ok; i++) {
            ok = q.offer("v");
            if (!ok) {
                Thread.yield();
            }
        }
        consumer.join(10000);
        if (!ok) {
            throw new RuntimeException("offer never succeeded with a consumer waiting");
        }
        if (consumer.isAlive()) {
            throw new RuntimeException("consumer did not receive element");
        }
        if (!"v".equals(got[0])) {
            throw new RuntimeException("unexpected element received: " + got[0]);
        }
    }
}
