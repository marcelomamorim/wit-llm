/*
 * @test    @summary Test drainTo method of LinkedBlockingQueue
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testDrainToEmptyQueue();
        testDrainToWithElements();
        testDrainToWithLimitedCapacity();
    }

    static void testDrainToEmptyQueue() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Collection<String> c = new ArrayList<>();
        int drained = queue.drainTo(c);
        assert drained == 0 : "drainTo on empty queue should return 0";
        assert c.isEmpty() : "Collection should remain empty after drainTo on empty queue";
    }

    static void testDrainToWithElements() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        Collection<String> c = new ArrayList<>();
        int drained = queue.drainTo(c);
        assert drained == 3 : "drainTo should drain all elements";
        assert c.size() == 3 : "Collection should contain 3 elements";
        assert c.contains("a") && c.contains("b") && c.contains("c") : "Collection should contain all drained elements";
        assert queue.isEmpty() : "Queue should be empty after drainTo";
    }

    static void testDrainToWithLimitedCapacity() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.offer("x");
        queue.offer("y");
        queue.offer("z");
        Collection<String> c = new ArrayList<>();
        int drained = queue.drainTo(c, 2);
        assert drained == 2 : "drainTo with limit 2 should drain 2 elements";
        assert c.size() == 2 : "Collection should contain 2 elements";
        assert (c.contains("x") || c.contains("y") || c.contains("z")) : "Collection should contain drained elements";
        assert queue.size() == 1 : "Queue should have 1 element remaining";
    }
}
