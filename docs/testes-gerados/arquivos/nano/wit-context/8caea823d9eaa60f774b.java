/*
 * @test
 * @summary Test for LinkedBlockingDeque.size() method.
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testSizeEmptyDeque();
        testSizeAfterAdd();
        testSizeAfterRemove();
    }

    static void testSizeEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        int size = deque.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0, but got " + size);
        }
    }

    static void testSizeAfterAdd() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        int size = deque.size();
        if (size != 1) {
            throw new AssertionError("Expected size 1, but got " + size);
        }
    }

    static void testSizeAfterRemove() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.remove();
        int size = deque.size();
        if (size != 0) {
            throw new AssertionError("Expected size 0 after removal, but got " + size);
        }
    }
}
