/*
 * @test
 * @summary Test drainTo method of LinkedTransferQueue for null argument
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.Collection;
import java.util.ArrayList;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testDrainToNullArgument();
        testDrainToNormal();
    }

    public static void testDrainToNullArgument() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        Collection<Object> c = null;
        boolean threwNullPointer = false;
        try {
            queue.drainTo(c);
        } catch (NullPointerException e) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException when argument is null");
        }
    }

    public static void testDrainToNormal() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("a");
        queue.add("b");
        ArrayList<String> list = new ArrayList<>();
        int count = queue.drainTo(list);
        if (count != 2) {
            throw new AssertionError("Expected drainTo to return 2, got " + count);
        }
        if (list.size() != 2 || !list.contains("a") || !list.contains("b")) {
            throw new AssertionError("Drain list does not contain expected elements");
        }
        if (queue.size() != 0) {
            throw new AssertionError("Queue should be empty after drainTo");
        }
    }
}
