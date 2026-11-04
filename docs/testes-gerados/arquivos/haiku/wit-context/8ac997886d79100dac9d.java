/*
 * @test
 * @summary Behavioral checks for LinkedBlockingDeque.take(): FIFO head retrieval and interrupt handling
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testTakeReturnsHeadInFifoOrder() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("a");
        deque.offerLast("b");
        deque.offerLast("c");
        check("a".equals(deque.take()), "first take() should return head 'a'");
        check("b".equals(deque.take()), "second take() should return 'b'");
        check("c".equals(deque.take()), "third take() should return 'c'");
        check(deque.isEmpty(), "deque should be empty after taking all elements");
    }

    static void testTakeRemovesElement() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.offerLast(Integer.valueOf(42));
        check(deque.size() == 1, "size should be 1 before take()");
        Integer value = deque.take();
        check(value.intValue() == 42, "take() should return 42");
        check(deque.size() == 0, "take() should remove the element");
    }

    static void testTakeInterruptedThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offerLast("x");
        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            deque.take();
        } catch (InterruptedException e) {
            thrown = true;
        }
        Thread.interrupted();
        check(thrown, "take() should throw InterruptedException when the thread is interrupted");
        check(deque.size() == 1, "element should not be removed when take() is interrupted");
    }

    public static void main(String[] args) throws Exception {
        testTakeReturnsHeadInFifoOrder();
        testTakeRemovesElement();
        testTakeInterruptedThrows();
    }
}
