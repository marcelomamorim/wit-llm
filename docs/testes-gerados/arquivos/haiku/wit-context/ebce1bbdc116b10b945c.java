/*
 * @test
 * @summary Verify ReferenceQueue.remove() blocks on an empty queue and reports interruption as InterruptedException
 * @run main ReferenceQueueWitupTest
 */

import java.lang.ref.ReferenceQueue;

public class ReferenceQueueWitupTest {
    public static void main(String[] args) throws Exception {
        final ReferenceQueue<Object> queue = new ReferenceQueue<>();
        final Throwable[] failure = new Throwable[1];
        final Object[] result = new Object[1];
        Thread t = new Thread(() -> {
            try {
                result[0] = queue.remove();
            } catch (Throwable e) {
                failure[0] = e;
            }
        });
        t.start();
        t.interrupt();
        t.join(10000);
        if (t.isAlive()) {
            throw new AssertionError("remove() did not return after interrupt");
        }
        if (!(failure[0] instanceof InterruptedException)) {
            throw new AssertionError("expected InterruptedException, got " + failure[0]);
        }
        if (result[0] != null) {
            throw new AssertionError("remove() returned a value on an empty queue");
        }
    }
}
