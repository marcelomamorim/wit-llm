/*
 * @test
 * @summary Tests LinkedBlockingDeque.addLast ordering, capacity bound and IllegalStateException when full
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddLastAppendsAtTailInOrder();
        testAddLastThrowsIllegalStateWhenFull();
        testAddLastOnUnboundedDequeDoesNotThrow();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddLastAppendsAtTailInOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        check(deque.size() == 3, "size should be 3 but was " + deque.size());
        check("a".equals(deque.peekFirst()), "first should be a");
        check("c".equals(deque.peekLast()), "last should be c");
        check("a".equals(deque.pollFirst()), "pollFirst should return a");
        check("b".equals(deque.pollFirst()), "pollFirst should return b");
        check("c".equals(deque.pollFirst()), "pollFirst should return c");
        check(deque.isEmpty(), "deque should be empty");
    }

    static void testAddLastThrowsIllegalStateWhenFull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<String>(1);
        deque.addLast("a");
        boolean thrown = false;
        try {
            deque.addLast("b");
        } catch (IllegalStateException e) {
            thrown = true;
            check("Deque full".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "addLast on full deque should throw IllegalStateException");
        check(deque.size() == 1, "size should remain 1 after failed addLast");
        check("a".equals(deque.peekLast()), "existing element should be preserved");
    }

    static void testAddLastOnUnboundedDequeDoesNotThrow() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>();
        for (int i = 0; i < 100; i++) {
            deque.addLast(Integer.valueOf(i));
        }
        check(deque.size() == 100, "size should be 100 but was " + deque.size());
        check(deque.peekLast().intValue() == 99, "last should be 99");
    }
}
