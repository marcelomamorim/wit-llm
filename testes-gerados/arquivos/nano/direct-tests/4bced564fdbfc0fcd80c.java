/* @test    @summary Test the contains method of LinkedBlockingDeque for various scenarios    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testContainsWithNull();
        testContainsWithEmptyDeque();
        testContainsWithPresentElement();
        testContainsWithAbsentElement();
    }

    static void testContainsWithNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean result = deque.contains(null);
        if (result) {
            throw new AssertionError("Expected false when checking contains(null) on empty deque");
        }
    }

    static void testContainsWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean result = deque.contains(1);
        if (result) {
            throw new AssertionError("Expected false when checking contains on empty deque");
        }
    }

    static void testContainsWithPresentElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        boolean result = deque.contains("test");
        if (!result) {
            throw new AssertionError("Expected true when element is present in deque");
        }
    }

    static void testContainsWithAbsentElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        boolean result = deque.contains("absent");
        if (result) {
            throw new AssertionError("Expected false when element is not present in deque");
        }
    }
}
