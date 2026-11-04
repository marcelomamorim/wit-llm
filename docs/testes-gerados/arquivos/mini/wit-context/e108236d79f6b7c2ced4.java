/*
 * @test
 * @summary Test LinkedTransferQueue.forEachRemaining with null and normal Consumer
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingWithElements();
    }

    static void testForEachRemainingNullAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        boolean npeThrown = false;
        try {
            queue.forEachRemaining(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    static void testForEachRemainingWithElements() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        queue.add("one");
        queue.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        queue.forEachRemaining(consumer);

        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two")) {
            throw new AssertionError("forEachRemaining did not process all elements: " + result);
        }
    }
}
