/*
 * @test
 * @summary Checks LinkedTransferQueue.tryTransfer returns false without a waiting consumer and does not enqueue, and succeeds when a consumer is waiting
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        LinkedTransferQueue<Integer> q = new LinkedTransferQueue<>();

        check(!q.tryTransfer(1), "tryTransfer on empty queue without consumer must return false");
        check(q.size() == 0, "failed tryTransfer must not enqueue on empty queue");
        check(q.poll() == null, "queue must remain empty after failed tryTransfer");

        q.offer(10);
        check(!q.tryTransfer(20), "tryTransfer with no waiting consumer must return false when data is queued");
        check(q.size() == 1, "failed tryTransfer must not add element to non-empty queue");
        check(Integer.valueOf(10).equals(q.poll()), "previously offered element must remain first");
        check(q.poll() == null, "failed tryTransfer element must not be present");

        final LinkedTransferQueue<Integer> q2 = new LinkedTransferQueue<>();
        final Integer[] got = new Integer[1];
        Thread consumer = new Thread(() -> {
            try {
                got[0] = q2.take();
            } catch (InterruptedException e) {
                // ignored
            }
        });
        consumer.start();

        long deadline = System.nanoTime() + 30_000_000_000L;
        boolean ok = false;
        while (!ok && System.nanoTime() < deadline) {
            ok = q2.tryTransfer(42);
            if (!ok) {
                Thread.sleep(1);
            }
        }
        consumer.join(30000);

        check(ok, "tryTransfer must succeed once a consumer is waiting");
        check(Integer.valueOf(42).equals(got[0]), "waiting consumer must receive the transferred element");
        check(q2.size() == 0, "successful transfer must not leave element in queue");
    }
}
