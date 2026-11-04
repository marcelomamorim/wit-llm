/* @test    @summary Test for Semaphore.acquireUninterruptibly(int permits) method behavior.    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testNegativePermitsThrowsException();
        testZeroPermitsDoesNotThrow();
        testPositivePermitsDoesNotThrow();
    }

    private static void testNegativePermitsThrowsException() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for permits < 0");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for permits < 0");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testZeroPermitsDoesNotThrow() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly(0);
            System.out.println("PASS: acquireUninterruptibly(0) did not throw");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for permits=0: " + t);
        }
    }

    private static void testPositivePermitsDoesNotThrow() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.acquireUninterruptibly(1);
            System.out.println("PASS: acquireUninterruptibly(1) did not throw");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for permits=1: " + t);
        }
    }
}
