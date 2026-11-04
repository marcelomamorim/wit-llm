/*
 * @test
 * @summary Test for Semaphore.release() method to observe its behavior.
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testRelease();
    }

    public static void testRelease() {
        Semaphore semaphore = new Semaphore(0);
        try {
            semaphore.release();
            int permits = semaphore.availablePermits();
            if (permits != 1) {
                System.err.println("Test failed: expected permits to be 1, but was " + permits);
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
        System.out.println("testRelease passed");
    }
}
