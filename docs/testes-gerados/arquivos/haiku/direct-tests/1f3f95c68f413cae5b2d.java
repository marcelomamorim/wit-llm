/*
 * @test
 * @summary Tests observable behavior of LinkedBlockingDeque.addFirst, including the IllegalStateException when a bounded deque is full
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddFirstUnboundedOrder() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.addFirst("a");
        deque.addFirst("b");
        deque.addFirst("c");
        check(deque.size() == 3, "size should be 3 but was " + deque.size());
        check("c".equals(deque.peekFirst()), "first should be c");
        check("a".equals(deque.peekLast()), "last should be a");
    }

    static void testAddFirstBoundedFullThrows() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>(1);
        deque.addFirst("a");
        boolean thrown = false;
        try {
            deque.addFirst("b");
        } catch (IllegalStateException e) {
            thrown = true;
            check("Deque full".equals(e.getMessage()),
                  "unexpected message: " + e.getMessage());
        }
        check(thrown, "addFirst on full deque should throw IllegalStateException");
        check(deque.size() == 1, "size should remain 1 after failed addFirst");
        check("a".equals(deque.peekFirst()), "element should be unchanged after failed addFirst");
    }

    static void testAddFirstBoundedCapacityBoundary() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.addFirst(1);
        deque.addFirst(2);
        check(deque.size() == 2, "size should be 2");
        boolean thrown = false;
        try {
            deque.addFirst(3);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "third addFirst should throw when capacity is 2");
        check(deque.peekFirst() == 2, "first should still be 2");
        check(deque.peekLast() == 1, "last should still be 1");
    }

    static void testAddFirstAfterCollectionConstructor() {
        LinkedBlockingDeque<String> deque =
            new LinkedBlockingDeque<>(Arrays.asList("x", "y"));
        check(deque.size() == 2, "initial size should be 2");
        deque.addFirst("z");
        check(deque.size() == 3, "size should be 3 after addFirst");
        check("z".equals(deque.peekFirst()), "first should be z");
        check("x".equals(deque.peekFirst() == null ? null : deque.toArray()[1]),
              "second element should be x");
    }

    public static void main(String[] args) throws Exception {
        testAddFirstUnboundedOrder();
        testAddFirstBoundedFullThrows();
        testAddFirstBoundedCapacityBoundary();
        testAddFirstAfterCollectionConstructor();
    }
}
