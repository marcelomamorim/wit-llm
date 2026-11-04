/*
 * @test
 * @summary Verify Thread.start runs the thread once and rejects a second start with IllegalThreadStateException
 * @run main ThreadWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadWitupTest {
    public static void main(String[] args) throws Exception {
        AtomicInteger runs = new AtomicInteger();
        Thread t = new Thread(() -> runs.incrementAndGet(), "witup-thread");

        t.start();
        try {
            t.start();
            throw new RuntimeException("expected IllegalThreadStateException on second start");
        } catch (IllegalThreadStateException expected) {
        }

        t.join();
        if (t.isAlive()) {
            throw new RuntimeException("thread still alive after join");
        }
        if (runs.get() != 1) {
            throw new RuntimeException("expected exactly one run, got " + runs.get());
        }

        try {
            t.start();
            throw new RuntimeException("expected IllegalThreadStateException after termination");
        } catch (IllegalThreadStateException expected) {
        }
        if (runs.get() != 1) {
            throw new RuntimeException("restart must not run target again, runs=" + runs.get());
        }
    }
}
