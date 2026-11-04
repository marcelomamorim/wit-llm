/*
 * @test
 * @summary Checks Semaphore.release(int) for negative, zero and positive permit counts
 * @run main SemaphoreWitupTest
 */

import java.util.concurrent.Semaphore;

public class SemaphoreWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativePermitsThrowsAndKeepsState();
        testZeroReleaseIsNoOp();
        testPositiveReleaseIncrementsPermits();
        testFairSemaphorePositiveRelease();
        testReleaseAfterAcquireRestoresPermits();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNegativePermitsThrowsAndKeepsState() {
        Semaphore s = new Semaphore(1);
        boolean thrown = false;
        try {
            s.release(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "release(-1) must throw IllegalArgumentException");
        check(s.availablePermits() == 1, "permits must not change after rejected release");
    }

    static void testZeroReleaseIsNoOp() {
        Semaphore s = new Semaphore(0);
        s.release(0);
        check(s.availablePermits() == 0, "release(0) must not add permits");
        check(!s.tryAcquire(), "no permit should be available after release(0)");
    }

    static void testPositiveReleaseIncrementsPermits() {
        Semaphore s = new Semaphore(0);
        s.release(3);
        check(s.availablePermits() == 3, "release(3) must add 3 permits");
        check(s.tryAcquire(), "permit should be acquirable after release");
        check(s.availablePermits() == 2, "one acquire should leave 2 permits");
    }

    static void testFairSemaphorePositiveRelease() {
        Semaphore s = new Semaphore(2, true);
        s.release(5);
        check(s.availablePermits() == 7, "fair semaphore release(5) must yield 7 permits");
    }

    static void testReleaseAfterAcquireRestoresPermits() {
        Semaphore s = new Semaphore(2);
        s.acquire(2);
        check(s.availablePermits() == 0, "all permits should be taken");
        s.release(2);
        check(s.availablePermits() == 2, "release(2) must restore both permits");
    }
}
