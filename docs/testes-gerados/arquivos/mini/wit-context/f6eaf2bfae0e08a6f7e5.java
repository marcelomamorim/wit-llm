/*
 * @test
 * @summary Test PriorityBlockingQueue.tryAdvance method behavior and exception
 * @run main PriorityBlockingQueueWitupTest
 */

import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Consumer;
import java.util.Objects;

public class PriorityBlockingQueueWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithValidAction();
        testTryAdvanceReturnsFalseWhenEmpty();
    }

    static void testTryAdvanceWithNullAction() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        // The tryAdvance method is from the PBQSpliterator inner class, but we test via public API
        // Since tryAdvance requires a non-null Consumer, passing null should throw NullPointerException
        try {
            queue.spliterator().tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceWithValidAction() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        queue.add("a");
        queue.add("b");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean advanced = queue.spliterator().tryAdvance(consumer);
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element is available");
        }
        String result = sb.toString();
        if (!"a".equals(result) && !"b".equals(result)) {
            throw new AssertionError("tryAdvance accepted unexpected element: " + result);
        }
    }

    static void testTryAdvanceReturnsFalseWhenEmpty() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("accept should not be called when queue is empty");
            }
        };

        boolean advanced = queue.spliterator().tryAdvance(consumer);
        if (advanced) {
            throw new AssertionError("tryAdvance should return false when no elements are available");
        }
    }
}
