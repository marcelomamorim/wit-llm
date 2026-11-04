/* @test    @summary Test removeFirst() method of LinkedBlockingDeque for correct behavior and exception handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.NoSuchElementException;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstWithElements();
        testRemoveFirstEmptyDeque();
    }

    static void testRemoveFirstWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element1");
        deque.add("element2");
        String removed = deque.removeFirst();
        assert "element1".equals(removed) : "Expected 'element1', got " + removed;
        assert deque.size() == 1 : "Expected size 1, got " + deque.size();
        assert "element2".equals(deque.peekFirst()) : "Expected 'element2' at front";
    }

    static void testRemoveFirstEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.removeFirst();
            assert false : "Expected NoSuchElementException";
        } catch (NoSuchElementException e) {
            // expected exception
        }
    }
}
