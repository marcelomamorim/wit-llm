/*
 * @test    @summary Test drainTo method of LinkedBlockingDeque
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.ArrayList;
import java.util.Collection;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testDrainToNormal();
        testDrainToWithMax();
        testDrainToWithEmptyDeque();
        testDrainToWithNullCollection();
        testDrainToWithSelf();
        testDrainToWithZeroMax();
    }

    static void testDrainToNormal() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        Collection<Integer> c = new ArrayList<>();
        int drained = deque.drainTo(c, 2);
        assert drained == 2 : "Expected drained=2, got " + drained;
        assert c.size() == 2 : "Collection size should be 2";
        assert c.contains(1) && c.contains(2) : "Collection should contain 1 and 2";
        assert deque.size() == 1 : "Deque should have 1 element left";
        assert deque.peek() == 3 : "Remaining element should be 3";
    }

    static void testDrainToWithMax() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(4);
        deque.add(5);
        Collection<Integer> c = new ArrayList<>();
        int drained = deque.drainTo(c, 5);
        assert drained == 2 : "Expected drained=2, got " + drained;
        assert c.size() == 2 : "Collection size should be 2";
        assert c.contains(4) && c.contains(5) : "Collection should contain 4 and 5";
        assert deque.isEmpty() : "Deque should be empty";
    }

    static void testDrainToWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        Collection<Integer> c = new ArrayList<>();
        int drained = deque.drainTo(c, 10);
        assert drained == 0 : "Expected drained=0";
        assert c.isEmpty() : "Collection should be empty";
    }

    static void testDrainToWithNullCollection() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        try {
            deque.drainTo(null, 1);
            assert false : "Should have thrown NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDrainToWithSelf() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(7);
        try {
            deque.drainTo(deque, 1);
            assert false : "Should have thrown IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDrainToWithZeroMax() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        deque.add(8);
        Collection<Integer> c = new ArrayList<>();
        int drained = deque.drainTo(c, 0);
        assert drained == 0 : "Expected drained=0";
        assert c.isEmpty() : "Collection should be empty";
        assert deque.size() == 1 : "Deque should still have 1 element";
    }
}
