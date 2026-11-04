/*
 * @test
 * @summary Verifies observable behavior of Thread.start(): thread is not alive before start, runs its target, and a second start (while alive or after termination) throws IllegalThreadStateException
 * @run main ThreadWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadWitupTest {

    public static void main(String[] args) throws Exception {
        testNotAliveBeforeStart();
        testTargetRunsOnStart();
        testStartAfterTerminationThrows();
        testStartWhileAliveThrows();
    }

    static void testNotAliveBeforeStart() {
        Thread t = new Thread(() -> { });
        if (t.isAlive()) {
            throw new RuntimeException("Thread must not be alive before start()");
        }
    }

    static void testTargetRunsOnStart() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger();
        Thread t = new Thread(counter::incrementAndGet);
        t.start();
        t.join();
        if (counter.get() != 1) {
            throw new RuntimeException("Target run count expected 1 but was " + counter.get());
        }
    }

    static void testStartAfterTerminationThrows() throws InterruptedException {
        Thread t = new Thread(() -> { });
        t.start();
        t.join();
        try {
            t.start();
            throw new RuntimeException("Expected IllegalThreadStateException on restart");
        } catch (IllegalThreadStateException expected) {
            // expected
        }
    }

    static void testStartWhileAliveThrows() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // ignore
            }
        });
        t.start();
        try {
            t.start();
            throw new RuntimeException("Expected IllegalThreadStateException on second start");
        } catch (IllegalThreadStateException expected) {
            // expected
        } finally {
            t.join();
        }
    }
}
