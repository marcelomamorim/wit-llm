/*
 * @test
 * @summary Verifies LinkedBlockingDeque.push prepends elements at the head of the deque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPushOnEmptyDeque();
        testPushPrependsInLifoOrder();
        testPushOnBoundedDeque();
        testPushAfterAddLast();
        System.out.println("All tests passed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testPushOnEmptyDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.push("a");
        check(d.size() == 1, "size after push on empty deque should be 1");
        check("a".equals(d.peekFirst()), "peekFirst should return pushed element");
        check("a".equals(d.peekLast()), "peekLast should return pushed element");
    }

    static void testPushPrependsInLifoOrder() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.push("a");
        d.push("b");
        d.push("c");
        check(d.size() == 3, "size should be 3");
        check("c".equals(d.peekFirst()), "last pushed element should be at head");
        check("a".equals(d.peekLast()), "first pushed element should be at tail");
        check("c".equals(d.pop()), "pop should return c");
        check("b".equals(d.pop()), "pop should return b");
        check("a".equals(d.pop()), "pop should return a");
        check(d.isEmpty(), "deque should be empty after popping all elements");
    }

    static void testPushOnBoundedDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        d.push(1);
        d.push(2);
        check(d.size() == 2, "size should be 2 on bounded deque");
        check(d.peekFirst() == 2, "head should be the most recent push");
        check(d.peekLast() == 1, "tail should be the first push");
    }

    static void testPushAfterAddLast() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("x");
        d.push("y");
        check(d.size() == 2, "size should be 2");
        check("y".equals(d.peekFirst()), "pushed element should be at head");
        check("x".equals(d.peekLast()), "element added with addLast should remain at tail");
    }
}
