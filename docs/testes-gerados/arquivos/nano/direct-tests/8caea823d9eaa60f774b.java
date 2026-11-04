/*
 * @test
 * @summary Test the size() method of LinkedBlockingDeque for correctness.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testEmptyDequeSize();
        testSingleElementSize();
        testMultipleElementsSize();
    }

    static void testEmptyDequeSize() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        int size = deque.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got " + size);
        }
    }

    static void testSingleElementSize() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        int size = deque.size();
        if (size != 1) {
            throw new AssertionError("Expected size 1, but got " + size);
        }
    }

    static void testMultipleElementsSize() {
        LinkedBlockingDeque<Double> deque = new LinkedBlockingDeque<>();
        deque.add(1.1);
        deque.add(2.2);
        deque.add(3.3);
        int size = deque.size();
        if (size != 3) {
            throw new AssertionError("Expected size 3, but got " + size);
        }
    }
}
