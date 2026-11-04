/*
 * @test
 * @summary CyclicBarrier.isBroken reports the broken state observably
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFreshBarrierIsNotBroken() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        check(!barrier.isBroken(), "new barrier must not be broken");
    }

    static void testSinglePartyAwaitDoesNotBreak() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(1);
        barrier.await();
        check(!barrier.isBroken(), "barrier must not be broken after normal trip");
    }

    static void testInterruptedBeforeAwaitBreaksBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Thread.currentThread().interrupt();
        try {
            barrier.await();
            throw new RuntimeException("await should throw InterruptedException");
        } catch (InterruptedException expected) {
            // expected; the interrupt status was cleared by the exception
        }
        check(barrier.isBroken(), "barrier must be broken after interrupted await");
    }

    static void testBarrierActionFailureBreaksBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(1, () -> {
            throw new IllegalStateException("action failure");
        });
        try {
            barrier.await();
            throw new RuntimeException("await should propagate action failure");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(barrier.isBroken(), "barrier must be broken after barrier action fails");
    }

    static void testResetClearsBrokenState() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        Thread.currentThread().interrupt();
        try {
            barrier.await();
        } catch (InterruptedException expected) {
            // breaks the barrier
        }
        check(barrier.isBroken(), "barrier must be broken before reset");
        barrier.reset();
        check(!barrier.isBroken(), "barrier must not be broken after reset");
    }

    public static void main(String[] args) throws Exception {
        testFreshBarrierIsNotBroken();
        testSinglePartyAwaitDoesNotBreak();
        testInterruptedBeforeAwaitBreaksBarrier();
        testBarrierActionFailureBreaksBarrier();
        testResetClearsBrokenState();
    }
}
