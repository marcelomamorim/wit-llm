/*
 * @test
 * @summary Verifies LinkedBlockingDeque(int capacity) rejects non-positive capacities and enforces the given bound
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        checkRejects(0);
        checkRejects(-1);
        checkRejects(Integer.MIN_VALUE);

        LinkedBlockingDeque<String> one = new LinkedBlockingDeque<>(1);
        if (!one.offer("a")) {
            throw new AssertionError("first offer should succeed for capacity 1");
        }
        if (one.offer("b")) {
            throw new AssertionError("second offer should fail for capacity 1");
        }
        if (one.size() != 1) {
            throw new AssertionError("size expected 1 but was " + one.size());
        }
        if (one.remainingCapacity() != 0) {
            throw new AssertionError("remainingCapacity expected 0 but was " + one.remainingCapacity());
        }

        LinkedBlockingDeque<String> two = new LinkedBlockingDeque<>(2);
        if (!two.offerLast("x") || !two.offerLast("y")) {
            throw new AssertionError("two offers should succeed for capacity 2");
        }
        if (two.offerFirst("z")) {
            throw new AssertionError("offerFirst should fail when deque is full");
        }
        if (two.size() != 2) {
            throw new AssertionError("size expected 2 but was " + two.size());
        }
    }

    private static void checkRejects(int capacity) {
        try {
            new LinkedBlockingDeque<Integer>(capacity);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for capacity " + capacity);
    }
}
