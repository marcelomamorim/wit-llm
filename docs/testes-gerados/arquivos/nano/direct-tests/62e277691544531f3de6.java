/* @test    @summary Test addLast method of LinkedBlockingDeque for normal and full capacity scenarios    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testAddLastNormal();
        testAddLastFullCapacity();
    }

    static void testAddLastNormal() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(2);
        deque.addLast(1);
        deque.addLast(2);
        assert deque.size() == 2 : "Size should be 2 after adding two elements";
        assert deque.peekFirst() == 1 : "First element should be 1";
        assert deque.peekLast() == 2 : "Last element should be 2";
    }

    static void testAddLastFullCapacity() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>(1);
        deque.addLast(10);
        boolean exceptionThrown = false;
        try {
            deque.addLast(20);
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Adding beyond capacity should throw IllegalStateException";
        assert deque.size() == 1 : "Size should remain 1 after failed add";
        assert deque.peekFirst() == 10 : "First element should be 10";
    }
}
