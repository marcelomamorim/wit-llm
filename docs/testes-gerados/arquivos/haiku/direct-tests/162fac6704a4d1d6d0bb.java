/*
 * @test
 * @summary Verify SynchronousQueue.offer returns false without a waiting consumer, throws NullPointerException for null, and hands off to a waiting consumer
 * @run main SynchronousQueueWitupTest
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) throws Exception {
        for (boolean fair : new boolean[]{false, true}) {
            SynchronousQueue<String> q = new SynchronousQueue<>(fair);

            if (q.offer("x")) {
                throw new AssertionError("offer without a waiting consumer must return false (fair=" + fair + ")");
            }
            if (q.poll() != null) {
                throw new AssertionError("poll after failed offer must return null (fair=" + fair + ")");
            }

            try {
                q.offer(null);
                throw new AssertionError("offer(null) must throw NullPointerException (fair=" + fair + ")");
            } catch (NullPointerException expected) {
                // expected
            }
        }

        SynchronousQueue<String> q = new SynchronousQueue<>();
        final String[] received = new String[1];
        final Throwable[] failure = new Throwable[1];
        Thread consumer = new Thread(() -> {
            try {
                received[0] = q.take();
            } catch (Throwable t) {
                failure[0] = t;
            }
        });
        consumer.start();

        long deadline = System.nanoTime() + 10_000_000_000L;
        boolean accepted = false;
        while (!accepted && System.nanoTime() < deadline) {
            accepted = q.offer("hello");
            if (!accepted) {
                Thread.sleep(1);
            }
        }
        consumer.join(10_000);

        if (failure[0] != null) {
            throw new AssertionError("consumer failed", failure[0]);
        }
        if (!accepted) {
            throw new AssertionError("offer must succeed once a consumer is waiting");
        }
        if (!"hello".equals(received[0])) {
            throw new AssertionError("consumer received unexpected value: " + received[0]);
        }
    }
}
