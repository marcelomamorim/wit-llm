/*
 * @test
 * @summary Test drainTo method of LinkedTransferQueue for correctness and exception handling
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testDrainToNormal();
        testDrainToNullCollection();
        testDrainToSelf();
        testDrainToMaxElementsZero();
        testDrainToMaxElementsNegative();
        testDrainToMaxElementsLarge();
    }

    static void testDrainToNormal() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        ArrayList<String> list = new ArrayList<>();
        int drained = queue.drainTo(list, 2);
        assert drained == 2 : "Expected drained=2, got " + drained;
        assert list.size() == 2 : "Expected list size=2, got " + list.size();
        assert list.contains("a") && list.contains("b") : "List does not contain expected elements";
        assert queue.size() == 1 : "Expected queue size=1, got " + queue.size();
        String remaining = queue.poll();
        assert remaining.equals("c") : "Expected remaining element 'c', got " + remaining;
    }

    static void testDrainToNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(null, 1);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDrainToSelf() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(queue, 1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDrainToMaxElementsZero() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("x");
        ArrayList<String> list = new ArrayList<>();
        int drained = queue.drainTo(list, 0);
        assert drained == 0 : "Expected drained=0, got " + drained;
        assert list.isEmpty() : "Expected empty list";
        assert queue.size() == 1 : "Queue should remain unchanged";
    }

    static void testDrainToMaxElementsNegative() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("y");
        ArrayList<String> list = new ArrayList<>();
        int drained = queue.drainTo(list, -5);
        assert drained == 0 : "Expected drained=0 for negative maxElements, got " + drained;
        assert list.isEmpty() : "Expected empty list";
        assert queue.size() == 1 : "Queue should remain unchanged";
    }

    static void testDrainToMaxElementsLarge() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }
        ArrayList<Integer> list = new ArrayList<>();
        int drained = queue.drainTo(list, 20);
        assert drained == 10 : "Expected drained=10, got " + drained;
        assert list.size() == 10 : "Expected list size=10, got " + list.size();
        for (int i = 0; i < 10; i++) {
            assert list.contains(i) : "List does not contain element " + i;
        }
        assert queue.isEmpty() : "Queue should be empty after draining all elements";
    }
}
