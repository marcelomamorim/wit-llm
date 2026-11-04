/*
 * @test
 * @summary Verifies Semaphore.release(int) adds permits, ignores zero, and rejects negative values
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testReleaseAddsPermits(new Semaphore(0));
        testReleaseAddsPermits(new Semaphore(0, true));
        testReleaseZeroIsNoOp();
        testNegativeReleaseThrows();
        testReleaseOnTopOfExistingPermits();
    }

    static void testReleaseAddsPermits(Semaphore s) {
        s.release(3);
        check(s.tryAcquire(), "first permit after release(3)");
        check(s.tryAcquire(), "second permit after release(3)");
        check(s.tryAcquire(), "third permit after release(3)");
        check(!s.tryAcquire(), "no fourth permit after release(3)");
    }

    static void testReleaseZeroIsNoOp() {
        Semaphore s = new Semaphore(1);
        s.release(0);
        check(s.tryAcquire(), "initial permit still present after release(0)");
        check(!s.tryAcquire(), "release(0) must not add a permit");
    }

    static void testNegativeReleaseThrows() {
        Semaphore s = new Semaphore(1);
        try {
            s.release(-1);
            throw new RuntimeException("release(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            s.release(Integer.MIN_VALUE);
            throw new RuntimeException("release(MIN_VALUE) should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(s.tryAcquire(), "permit count unchanged after rejected release");
        check(!s.tryAcquire(), "no extra permit added by rejected release");
    }

    static void testReleaseOnTopOfExistingPermits() {
        Semaphore s = new Semaphore(2);
        s.release(2);
        for (int i = 0; i < 4; i++) {
            check(s.tryAcquire(), "permit " + i + " available");
        }
        check(!s.tryAcquire(), "no permit beyond 4 total");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
