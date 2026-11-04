/* @test    @summary Test for LinkedBlockingDeque.toArray() method to observe behavior and exception handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testToArrayNormal();
        testToArrayEmpty();
    }

    static void testToArrayNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        Object[] array = deque.toArray();
        boolean condition = array.length == 2 && array[0].equals("one") && array[1].equals("two");
        if (!condition) {
            throw new AssertionError("Unexpected array contents: " + java.util.Arrays.toString(array));
        }
    }

    static void testToArrayEmpty() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        Object[] array = deque.toArray();
        if (array.length != 0) {
            throw new AssertionError("Expected empty array, got: " + java.util.Arrays.toString(array));
        }
    }
}
