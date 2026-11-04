/* @test    @summary Test addFirst method of LinkedBlockingDeque for normal and full capacity scenarios    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddFirstNormal();
        testAddFirstFullCapacity();
    }

    static void testAddFirstNormal() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.addFirst(1);
        deque.addFirst(2);
        assert deque.size() == 2 : "Size should be 2";
        assert deque.peekFirst() == 2 : "First element should be 2";
        assert deque.peekLast() == 1 : "Last element should be 1";
    }

    static void testAddFirstFullCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        deque.addFirst(10);
        try {
            deque.addFirst(20);
            assert false : "Should have thrown IllegalStateException";
        } catch (IllegalStateException e) {
            assert e.getMessage().equals("Deque full") : "Exception message mismatch";
        }
        assert deque.size() == 1 : "Size should remain 1";
        assert deque.peekFirst() == 10 : "First element should be 10";
    }
}
