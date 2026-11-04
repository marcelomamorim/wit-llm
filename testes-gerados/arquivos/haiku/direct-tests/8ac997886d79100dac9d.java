/*
 * @test
 * @summary Witup-style tests for LinkedBlockingDeque.take()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicReference;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testTakeReturnsElementsInFifoOrder();
        testTakeBlocksUntilElementAvailable();
        testTakeOnEmptyDequeWithInterruptedFlagThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testTakeReturnsElementsInFifoOrder() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        check(deque.take().intValue() == 1, "first take should return 1");
        check(deque.take().intValue() == 2, "second take should return 2");
        check(deque.take().intValue() == 3, "third take should return 3");
        check(deque.isEmpty(), "deque should be empty after taking all elements");
    }

    static void testTakeBlocksUntilElementAvailable() throws InterruptedException {
        final LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicReference<String> result = new AtomicReference<>();
        Thread taker = new Thread(() -> {
            try {
                result.set(deque.take());
            } catch (InterruptedException e) {
                result.set("interrupted");
            }
        });
        taker.start();
        Thread.sleep(100);
        deque.offer("x");
        taker.join(10000);
        check(!taker.isAlive(), "taker thread should finish after an element is offered");
        check("x".equals(result.get()), "take should return the offered element");
    }

    static void testTakeOnEmptyDequeWithInterruptedFlagThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Thread.currentThread().interrupt();
        try {
            deque.take();
            throw new AssertionError("expected InterruptedException");
        } catch (InterruptedException expected) {
            // expected
        }
        check(!Thread.currentThread().isInterrupted(),
              "interrupt status should be cleared after InterruptedException");
    }
}
