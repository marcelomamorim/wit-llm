/*
 * @test
 * @summary Test LinkedTransferQueue.forEachRemaining behavior
 * @run main LinkedTransferQueueWitupTest
 */

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.LinkedTransferQueue.Node;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedTransferQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithEmptyQueue();
        testForEachRemainingWithElements();
    }

    static void testForEachRemainingWithNullAction() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        try {
            queue.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingWithEmptyQueue() {
        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();
        final boolean[] called = {false};
        queue.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new RuntimeException("Consumer should not be called on empty queue");
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

        // Use iterator to get current node and set it to queue's current field via reflection
        // Because current() and current field are package-private or private, we cannot access directly
        // So we test the observable effect by calling forEachRemaining on the iterator

        java.util.Iterator<String> it = queue.iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator should have elements");
        }

        // Consume one element to move iterator forward
        it.next();

        // Now call forEachRemaining on the iterator
        it.forEachRemaining(consumer);

        String result = sb.toString();
        if (!result.equals("two,")) {
            throw new RuntimeException("Expected 'two,' but got: " + result);
        }
    }
}
