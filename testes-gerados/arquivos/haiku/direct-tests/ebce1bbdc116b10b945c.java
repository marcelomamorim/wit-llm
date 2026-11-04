/*
 * @test
 * @summary ReferenceQueue.remove() delegates to remove(0) and propagates InterruptedException when the thread is interrupted
 * @run main ReferenceQueueWitupTest
 */

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class ReferenceQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testInterruptedBeforeRemove();
        testInterruptedWhileBlocked();
    }

    static void testInterruptedBeforeRemove() {
        ReferenceQueue<Object> q = new ReferenceQueue<>();
        Thread.currentThread().interrupt();
        try {
            Reference<? extends Object> r = q.remove();
            throw new RuntimeException("expected InterruptedException, got " + r);
        } catch (InterruptedException e) {
            // expected
        } finally {
            Thread.interrupted();
        }
    }

    static void testInterruptedWhileBlocked() throws Exception {
        ReferenceQueue<Object> q = new ReferenceQueue<>();
        final Throwable[] result = new Throwable[1];
        Thread t = new Thread(() -> {
            try {
                q.remove();
            } catch (InterruptedException e) {
                result[0] = e;
            }
        });
        t.start();
        t.interrupt();
        t.join(60000);
        if (t.isAlive()) {
            t.interrupt();
            throw new RuntimeException("remove() did not return after interrupt");
        }
        if (!(result[0] instanceof InterruptedException)) {
            throw new RuntimeException("expected InterruptedException, got " + result[0]);
        }
    }
}
