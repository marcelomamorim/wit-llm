/*
 * @test
 * @summary Test LinkedBlockingQueue.tryAdvance behavior including null action and normal iteration
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceNormal();
        testTryAdvanceExhausted();
    }

    static void testTryAdvanceNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        try {
            queue.spliterator().tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceNormal() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        queue.add("one");
        queue.add("two");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced1 = queue.spliterator().tryAdvance(consumer);
        if (!advanced1) {
            throw new RuntimeException("tryAdvance should return true when element present");
        }
        if (!sb.toString().equals("one")) {
            throw new RuntimeException("Consumer did not receive expected element 'one'");
        }

        sb.setLength(0);
        boolean advanced2 = queue.spliterator().tryAdvance(consumer);
        if (!advanced2) {
            throw new RuntimeException("tryAdvance should return true for second element");
        }
        if (!sb.toString().equals("two")) {
            throw new RuntimeException("Consumer did not receive expected element 'two'");
        }
    }

    static void testTryAdvanceExhausted() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        // empty queue spliterator tryAdvance returns false
        boolean advanced = queue.spliterator().tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new RuntimeException("Should not be called on empty queue");
            }
        });
        if (advanced) {
            throw new RuntimeException("tryAdvance should return false when no elements");
        }
    }
}
