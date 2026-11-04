/*
 * @test
 * @summary Test ArrayBlockingQueue.forEachRemaining with null and valid Consumer
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    static void testForEachRemainingNullAction() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        // The forEachRemaining method is on the iterator, so get iterator
        ArrayBlockingQueue<String>.Itr it = queue.new Itr();
        try {
            it.forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingValidAction() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("a");
        queue.add("b");

        ArrayBlockingQueue<String>.Itr it = queue.new Itr();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        it.forEachRemaining(action);

        // The iterator should consume all elements remaining
        String result = sb.toString();
        if (!result.equals("ab")) {
            throw new RuntimeException("forEachRemaining did not process all elements, got: " + result);
        }

        // After forEachRemaining, iterator should be detached and no further elements
        try {
            it.forEachRemaining(action); // should do nothing silently
        } catch (Exception e) {
            throw new RuntimeException("forEachRemaining threw after completion", e);
        }
    }
}
