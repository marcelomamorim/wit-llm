/*
 * @test
 * @summary Tests for LinkedTransferQueue methods, focusing on exception paths and behavior.
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.NoSuchElementException;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithValidAction();
    }

    public static void testForEachRemainingWithNullAction() {
        LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
        try {
            queue.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    public static void testForEachRemainingWithValidAction() {
        LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
        queue.add(1);
        queue.add(2);
        final StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        queue.forEachRemaining(consumer);
        if (!"12".equals(sb.toString())) {
            throw new AssertionError("Unexpected result: " + sb.toString());
        }
    }
}
