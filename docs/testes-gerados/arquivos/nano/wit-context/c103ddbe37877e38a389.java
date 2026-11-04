/*
 * @test
 * @summary Tests for LinkedBlockingDeque retainAll method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRetainAllWithNull();
        testRetainAllWithNonNull();
    }

    public static void testRetainAllWithNull() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        boolean exceptionThrown = false;
        try {
            deque.retainAll(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to retainAll");
        }
    }

    public static void testRetainAllWithNonNull() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = java.util.Arrays.asList(2, 3);
        boolean result = deque.retainAll(c);
        if (!result) {
            throw new AssertionError("retainAll should return true when deque is modified");
        }
        if (deque.size() != 2 || !deque.contains(2) || !deque.contains(3)) {
            throw new AssertionError("Deque contents do not match expected after retainAll");
        }
    }
}
