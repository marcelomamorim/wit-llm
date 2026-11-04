/*
 * @test
 * @summary removeLast returns the last element and throws NoSuchElementException when the deque is empty
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveLastOrder();
        testRemoveLastOnEmptyThrows();
        testRemoveLastAfterConstructorFromCollection();
        testRemoveLastAfterOfferLastOnBounded();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveLastOrder() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("c");
        check("c".equals(d.removeLast()), "first removeLast should return c");
        check(d.size() == 2, "size should be 2 after one removeLast");
        check("b".equals(d.removeLast()), "second removeLast should return b");
        check("a".equals(d.removeLast()), "third removeLast should return a");
        check(d.isEmpty(), "deque should be empty after removing all elements");
    }

    static void testRemoveLastOnEmptyThrows() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        boolean thrown = false;
        try {
            d.removeLast();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "removeLast on empty deque should throw NoSuchElementException");
        check(d.isEmpty(), "deque should remain empty after failed removeLast");
    }

    static void testRemoveLastAfterConstructorFromCollection() {
        LinkedBlockingDeque<Integer> d =
            new LinkedBlockingDeque<>(Arrays.asList(1, 2, 3));
        check(d.removeLast() == 3, "removeLast should return 3 for collection-constructed deque");
        check(d.removeLast() == 2, "removeLast should return 2 next");
        check(d.size() == 1, "size should be 1");
    }

    static void testRemoveLastAfterOfferLastOnBounded() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(2);
        check(d.offerLast("x"), "offerLast should succeed on bounded deque");
        check(d.offerLast("y"), "offerLast should succeed up to capacity");
        check("y".equals(d.removeLast()), "removeLast should return y");
        check(d.offerLast("z"), "offerLast should succeed after removal frees capacity");
        check("z".equals(d.removeLast()), "removeLast should return z");
        check("x".equals(d.removeLast()), "removeLast should return x");
        check(d.isEmpty(), "deque should be empty");
    }
}
