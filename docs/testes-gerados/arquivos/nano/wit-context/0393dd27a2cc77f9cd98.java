/*
 * @test
 * @summary Tests for LinkedBlockingDeque.drainTo(Collection) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testDrainToWithNullCollection();
        testDrainToWithEmptyDeque();
        testDrainToWithNonEmptyDeque();
    }

    static void testDrainToWithNullCollection() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.drainTo(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testDrainToWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Collection<Integer> c = new ArrayList<>();
        int result = deque.drainTo(c);
        assert result == 0 : "drainTo on empty deque should return 0";
        assert c.isEmpty() : "Collection should remain empty after drainTo on empty deque";
    }

    static void testDrainToWithNonEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = new ArrayList<>();
        int result = deque.drainTo(c);
        assert result == 3 : "drainTo should return number of elements drained";
        assert c.size() == 3 : "Collection should contain drained elements";
        assert c.contains(1) && c.contains(2) && c.contains(3) : "Collection should contain all drained elements";
        assert deque.isEmpty() : "Deque should be empty after drainTo";
    }
}
