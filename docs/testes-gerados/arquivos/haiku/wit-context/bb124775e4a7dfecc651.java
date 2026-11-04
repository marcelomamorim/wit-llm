/*
 * @test
 * @summary WITUP-derived checks for LinkedBlockingDeque(int capacity) argument validation and observable capacity
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
        expectIllegalArgument(Integer.MIN_VALUE);

        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        if (deque.remainingCapacity() != 1) {
            throw new AssertionError("expected remainingCapacity 1 but was " + deque.remainingCapacity());
        }
        if (!deque.offerLast(Integer.valueOf(1))) {
            throw new AssertionError("offerLast should succeed while below capacity");
        }
        if (deque.offerLast(Integer.valueOf(2))) {
            throw new AssertionError("offerLast should fail when capacity is reached");
        }
        if (deque.remainingCapacity() != 0) {
            throw new AssertionError("expected remainingCapacity 0 but was " + deque.remainingCapacity());
        }

        LinkedBlockingDeque<Integer> larger = new LinkedBlockingDeque<>(3);
        if (larger.remainingCapacity() != 3) {
            throw new AssertionError("expected remainingCapacity 3 but was " + larger.remainingCapacity());
        }
    }

    private static void expectIllegalArgument(int capacity) {
        try {
            new LinkedBlockingDeque<Integer>(capacity);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for capacity " + capacity);
    }
}
