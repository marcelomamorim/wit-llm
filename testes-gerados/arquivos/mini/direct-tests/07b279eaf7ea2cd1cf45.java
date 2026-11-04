/*
 * @test
 * @summary Basic test for Semaphore.release() increments permits and unblocks acquirers
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {
    public static void main(String[] args) throws Exception {
        final Semaphore sem = new Semaphore(0);

        // Start a thread that acquires a permit (will block)
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    sem.acquire();
                    // If acquire returns, test passes for release unblocking
                    System.out.println("acquire succeeded");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();

        // Sleep briefly to ensure thread is blocked on acquire
        Thread.sleep(100);

        // Release a permit, should unblock the thread
        sem.release();

        // Wait for thread to finish
        t.join(1000);

        if (t.isAlive()) {
            throw new AssertionError("Thread did not unblock after release");
        }

        // Test that release increments permits count
        Semaphore sem2 = new Semaphore(1);
        sem2.acquire(); // permits now 0
        sem2.release(); // permits now 1
        sem2.release(); // permits now 2

        // Try to acquire twice without blocking
        sem2.acquire();
        sem2.acquire();

        // If no exception, test passes
        System.out.println("release increments permits correctly");
    }
}
