/*
 * @test    @summary Test drainTo method of LinkedTransferQueue
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.ArrayList;
import java.util.Collection;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testDrainToEmptyQueue();
        testDrainToWithElements();
        testDrainToNullCollection();
        testDrainToSelf();
    }

    static void testDrainToEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        Collection<String> c = new ArrayList<>();
        int drained = queue.drainTo(c);
        assert drained == 0 : "drainTo on empty queue should return 0";
        assert c.isEmpty() : "Collection should remain empty after drain";
    }

    static void testDrainToWithElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        Collection<String> c = new ArrayList<>();
        int drained = queue.drainTo(c);
        assert drained == 3 : "drainTo should drain all elements";
        assert c.size() == 3 : "Collection should contain 3 elements";
        assert c.contains("a") && c.contains("b") && c.contains("c") : "Collection should contain all elements";
        assert queue.isEmpty() : "Queue should be empty after drain";
    }

    static void testDrainToNullCollection() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.drainTo(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testDrainToSelf() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.offer("x");
        try {
            queue.drainTo((Collection<String>)queue);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
