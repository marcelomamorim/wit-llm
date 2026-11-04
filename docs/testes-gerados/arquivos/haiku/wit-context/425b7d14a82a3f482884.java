/*
 * @test
 * @summary Witup-derived checks for LinkedBlockingDeque.putFirst
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) throws Exception {
        testNullThrowsNPE();
        testInsertFirstOrder();
        testUnboundedDefaultConstructor();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullThrowsNPE() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        boolean thrown = false;
        try {
            deque.putFirst(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "putFirst(null) must throw NullPointerException");
        check(deque.isEmpty(), "deque must remain empty after rejected null");
    }

    static void testInsertFirstOrder() throws InterruptedException {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(2);
        deque.putFirst("a");
        deque.putFirst("b");
        check(deque.size() == 2, "size must be 2");
        check("b".equals(deque.peekFirst()), "last putFirst element must be at head");
        check("a".equals(deque.peekLast()), "first putFirst element must be at tail");
        check(deque.remainingCapacity() == 0, "remaining capacity must be 0 when full");
    }

    static void testUnboundedDefaultConstructor() throws InterruptedException {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        for (int i = 0; i < 100; i++) {
            deque.putFirst(i);
        }
        check(deque.size() == 100, "size must be 100");
        check(deque.peekFirst() == 99, "head must be last inserted element");
        check(deque.peekLast() == 0, "tail must be first inserted element");
    }
}
