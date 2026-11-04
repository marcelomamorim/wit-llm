/*
 * @test
 * @summary Witup-derived checks for CyclicBarrier.isBroken() observable behavior
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFreshBarrierNotBroken() {
        CyclicBarrier barrier = new CyclicBarrier(2);
        check(!barrier.isBroken(), "new barrier must not be broken");
    }

    static void testSinglePartyAwaitDoesNotBreak() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(1);
        int index = barrier.await();
        check(index == 0, "single party arrival index must be 0");
        check(!barrier.isBroken(), "barrier must not be broken after normal trip");
    }

    static void testTimeoutBreaksBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        boolean timedOut = false;
        try {
            barrier.await(1, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            timedOut = true;
        }
        check(timedOut, "await with no other party must time out");
        check(barrier.isBroken(), "barrier must be broken after timeout");
    }

    static void testFailingBarrierActionBreaksBarrier() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(1, new Runnable() {
            public void run() {
                throw new IllegalStateException("action failure");
            }
        });
        boolean thrown = false;
        try {
            barrier.await();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "barrier action exception must propagate");
        check(barrier.isBroken(), "barrier must be broken when barrier action fails");
    }

    static void testResetClearsBrokenState() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        try {
            barrier.await(1, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // expected
        }
        check(barrier.isBroken(), "precondition: barrier must be broken");
        barrier.reset();
        check(!barrier.isBroken(), "reset must clear broken state");
    }

    static void testAwaitOnBrokenBarrierThrows() throws Exception {
        CyclicBarrier barrier = new CyclicBarrier(2);
        try {
            barrier.await(1, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            // expected
        }
        boolean thrown = false;
        try {
            barrier.await();
        } catch (BrokenBarrierException e) {
            thrown = true;
        }
        check(thrown, "await on broken barrier must throw BrokenBarrierException");
        check(barrier.isBroken(), "barrier must remain broken");
    }

    public static void main(String[] args) throws Exception {
        testFreshBarrierNotBroken();
        testSinglePartyAwaitDoesNotBreak();
        testTimeoutBreaksBarrier();
        testFailingBarrierActionBreaksBarrier();
        testResetClearsBrokenState();
        testAwaitOnBrokenBarrierThrows();
    }
}
