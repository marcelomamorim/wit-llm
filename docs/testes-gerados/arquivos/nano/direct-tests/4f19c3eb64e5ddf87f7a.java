/* @test    @summary Test pollFirst() method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testPollFirstEmptyDeque();
        testPollFirstWithElements();
    }

    private static void testPollFirstEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Integer result = deque.pollFirst();
        if (result != null) {
            throw new AssertionError("Expected null when polling from empty deque, but got: " + result);
        }
    }

    private static void testPollFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.offer("A");
        deque.offer("B");
        String first = deque.pollFirst();
        if (!"A".equals(first)) {
            throw new AssertionError("Expected 'A' as first element, but got: " + first);
        }
        String second = deque.pollFirst();
        if (!"B".equals(second)) {
            throw new AssertionError("Expected 'B' as second element, but got: " + second);
        }
        String resultAfterAll = deque.pollFirst();
        if (resultAfterAll != null) {
            throw new AssertionError("Expected null after removing all elements, but got: " + resultAfterAll);
        }
    }
}
