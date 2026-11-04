/*
 * @test
 * @summary Test java.lang.Thread join() method behavior
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) throws Exception {
        testJoinReturnsAfterThreadTerminates();
        testJoinInterruptsProperly();
    }

    private static void testJoinReturnsAfterThreadTerminates() throws Exception {
        final Thread t = new Thread(new Runnable() {
            public void run() {
                // do nothing, just terminate
            }
        });
        t.start();
        t.join(); // should return after thread terminates
        if (t.isAlive()) {
            throw new RuntimeException("Thread should have terminated after join");
        }
    }

    private static void testJoinInterruptsProperly() throws Exception {
        final Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        });
        t.start();

        Thread.currentThread().interrupt(); // interrupt current thread
        try {
            t.join();
            throw new RuntimeException("join() should throw InterruptedException when interrupted");
        } catch (InterruptedException e) {
            // expected
        } finally {
            // clear interrupt status for subsequent tests
            Thread.interrupted();
        }

        t.interrupt(); // interrupt the other thread to finish quickly
        t.join(); // wait for thread to finish
    }
}
