/*
 * @test
 * @summary Basic deterministic test for java.lang.Thread.join()
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a thread that does nothing and start it
        Thread t = new Thread();
        t.start();

        // Join the thread - should return normally after thread terminates
        t.join();

        // Join again on the same thread - should return immediately
        t.join();

        // Create a thread that sleeps briefly
        Thread sleeper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        });
        sleeper.start();

        // Join the sleeper thread - should wait until it finishes
        sleeper.join();

        // Test that join() throws InterruptedException if interrupted
        final Thread joinThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().join(); // join on self, returns immediately
                } catch (InterruptedException e) {
                    // Should not happen here
                    throw new RuntimeException(e);
                }
            }
        });

        joinThread.start();

        // Interrupt the main thread while it is joining joinThread
        Thread mainThread = Thread.currentThread();
        Thread interrupter = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    // Ignore
                }
                mainThread.interrupt();
            }
        });

        interrupter.start();

        boolean interruptedThrown = false;
        try {
            joinThread.join(); // This join should be interrupted
        } catch (InterruptedException e) {
            interruptedThrown = true;
        }

        if (!interruptedThrown) {
            throw new RuntimeException("Expected InterruptedException was not thrown");
        }

        // Clear interrupt status for further tests
        Thread.interrupted();
    }
}
