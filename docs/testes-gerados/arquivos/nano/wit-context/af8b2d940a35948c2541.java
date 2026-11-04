/* @test    @summary Test for Semaphore.release(int permits) method to verify behavior with valid and invalid permits    @run main SemaphoreWitupTest */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) {
        testReleaseWithNegativePermits();
        testReleaseWithZeroPermits();
        testReleaseWithPositivePermits();
    }

    private static void testReleaseWithNegativePermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.release(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative permits");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative permits");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testReleaseWithZeroPermits() {
        Semaphore semaphore = new Semaphore(1);
        try {
            semaphore.release(0);
            System.out.println("PASS: release(0) executed without exception");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for release(0): " + t);
        }
    }

    private static void testReleaseWithPositivePermits() {
        Semaphore semaphore = new Semaphore(0);
        int permitsToRelease = 3;
        try {
            semaphore.release(permitsToRelease);
            if (semaphore.availablePermits() == permitsToRelease) {
                System.out.println("PASS: release(" + permitsToRelease + ") increased permits correctly");
            } else {
                System.out.println("FAIL: Permits count mismatch after release");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for release(