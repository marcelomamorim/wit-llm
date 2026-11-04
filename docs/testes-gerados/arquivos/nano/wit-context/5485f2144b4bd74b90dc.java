/* @test    @summary Test for clear() method in LinkedBlockingDeque to observe behavior and exception handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testClearOnEmptyDeque();
        testClearOnPopulatedDeque();
    }

    static void testClearOnEmptyDeque() {
        LinkedBlockingDeque<Object> deque = new LinkedBlockingDeque<>();
        try {
            deque.clear();
            System.out.println("testClearOnEmptyDeque passed");
        } catch (Exception e) {
            System.err.println("testClearOnEmptyDeque failed: " + e);
        }
    }

    static void testClearOnPopulatedDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        try {
            deque.clear();
            if (deque.isEmpty()) {
                System.out.println("testClearOnPopulatedDeque passed");
            } else {
                System.err.println("testClearOnPopulatedDeque failed: deque not empty after clear");
            }
        } catch (Exception e) {
            System.err.println("testClearOnPopulatedDeque failed: " + e);
        }
    }
}
