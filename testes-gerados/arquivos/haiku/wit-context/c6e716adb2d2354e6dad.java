/*
 * @test
 * @summary Witup-generated checks for LinkedBlockingDeque.pop() on empty and non-empty deques
 * @run main LinkedBlockingDequeWitupTest
 */
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();

        boolean thrown = false;
        try {
            deque.pop();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("pop() on empty deque must throw NoSuchElementException");
        }

        deque.push("a");
        deque.push("b");
        deque.addLast("c");

        check(deque.pop(), "b");
        check(deque.pop(), "a");
        check(deque.pop(), "c");

        if (!deque.isEmpty()) {
            throw new RuntimeException("deque must be empty after removing all elements");
        }

        thrown = false;
        try {
            deque.pop();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("pop() after draining deque must throw NoSuchElementException");
        }
    }

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
