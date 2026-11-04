/* @test    @summary Test the remove() method of LinkedBlockingDeque for correct behavior    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveReturnsFirstElement();
        testRemoveOnEmptyDequeReturnsNull();
    }

    private static void testRemoveReturnsFirstElement() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        String removed = deque.remove();
        if (!"element1".equals(removed)) {
            throw new AssertionError("Expected 'element1', but got: " + removed);
        }
        if (deque.size() != 1) {
            throw new AssertionError("Deque size should be 1 after removal, but is: " + deque.size());
        }
        String remaining = deque.peek();
        if (!"element2".equals(remaining)) {
            throw new AssertionError("Remaining element should be 'element2', but is: " + remaining);
        }
    }

    private static void testRemoveOnEmptyDequeReturnsNull() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        String result = deque.remove();
        if (result != null) {
            throw new AssertionError("Expected null when removing from empty deque, but got: " + result);
        }
    }
}
