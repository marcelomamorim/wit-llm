/*
 * @test
 * @summary Verifies public observable behavior of LinkedBlockingDeque.pop(), which delegates to removeFirst()
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPopReturnsMostRecentlyPushedFirst();
        testPopOnEmptyDequeThrowsNoSuchElementException();
        testPopRemovesHeadAndDecrementsSize();
        testPopOnCollectionConstructedDequeReturnsFirstElement();
        testPopOnBoundedDequeAfterOffers();
        testDequeRemainsUsableAfterPopOnEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testPopReturnsMostRecentlyPushedFirst() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.push(1);
        d.push(2);
        d.push(3);
        check(d.pop() == 3, "pop should return last pushed element 3");
        check(d.pop() == 2, "pop should return next element 2");
        check(d.pop() == 1, "pop should return element 1");
    }

    static void testPopOnEmptyDequeThrowsNoSuchElementException() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        try {
            d.pop();
            throw new AssertionError("pop on empty deque should throw NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // expected
        }
    }

    static void testPopRemovesHeadAndDecrementsSize() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        d.addLast("b");
        check(d.size() == 2, "size should be 2 before pop");
        String head = d.pop();
        check("a".equals(head), "pop should return head a");
        check(d.size() == 1, "size should be 1 after pop");
        check("b".equals(d.peekFirst()), "remaining head should be b");
    }

    static void testPopOnCollectionConstructedDequeReturnsFirstElement() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(10, 20, 30));
        check(d.pop() == 10, "pop should return first element of source collection");
        check(d.size() == 2, "size should be 2 after one pop");
    }

    static void testPopOnBoundedDequeAfterOffers() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        check(d.offerLast(7), "offerLast should succeed under capacity");
        check(d.offerLast(8), "offerLast should succeed under capacity");
        check(!d.offerLast(9), "offerLast should fail at capacity");
        check(d.pop() == 7, "pop should return 7");
        check(d.offerLast(9), "offerLast should succeed after pop frees capacity");
        check(d.pop() == 8, "pop should return 8");
        check(d.pop() == 9, "pop should return 9");
    }

    static void testDequeRemainsUsableAfterPopOnEmpty() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        try {
            d.pop();
        } catch (NoSuchElementException expected) {
            // expected
        }
        d.push(5);
        check(d.pop() == 5, "deque should work normally after failed pop");
        check(d.isEmpty(), "deque should be empty after popping its only element");
    }
}
