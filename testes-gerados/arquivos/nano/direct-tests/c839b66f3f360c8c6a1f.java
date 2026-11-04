/* @test    @summary Test remainingCapacity() method of LinkedBlockingDeque    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemainingCapacityZero();
        testRemainingCapacityPositive();
        testRemainingCapacityFull();
    }

    private static void testRemainingCapacityZero() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(0);
        int capacity = deque.remainingCapacity();
        if (capacity != 0) {
            throw new AssertionError("Expected remainingCapacity to be 0, but was " + capacity);
        }
    }

    private static void testRemainingCapacityPositive() {
        int cap = 10;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(cap);
        deque.offer(1);
        int remaining = deque.remainingCapacity();
        if (remaining != cap - 1) {
            throw new AssertionError("Expected remainingCapacity to be " + (cap - 1) + ", but was " + remaining);
        }
    }

    private static void testRemainingCapacityFull() {
        int cap = 2;
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(cap);
        deque.offer(1);
        deque.offer(2);
        int remaining = deque.remainingCapacity();
        if (remaining != 0) {
            throw new AssertionError("Expected remainingCapacity to be 0, but was " + remaining);
        }
    }
}
