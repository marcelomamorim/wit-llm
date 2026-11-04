/*
 * @test
 * @summary Test LinkedBlockingQueue.LBQSpliterator.forEachRemaining behavior
 * @run main LinkedBlockingQueueWitupTest
 */

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue.LBQSpliterator;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    // Test that forEachRemaining throws NullPointerException if action is null
    static void testForEachRemainingNullAction() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        LBQSpliterator<String> spliterator = queue.new LBQSpliterator<>(null, null, 0, 0, false);
        try {
            spliterator.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that forEachRemaining calls forEachFrom once and sets exhausted to true
    static void testForEachRemainingNormal() {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        LBQSpliterator<String> spliterator = queue.new LBQSpliterator<>(null, null, 0, 0, false);

        // Setup a Consumer that records invocation
        final boolean[] called = {false};
        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        };

        // current is null, exhausted is false initially
        spliterator.current = null;
        spliterator.exhausted = false;

        spliterator.forEachRemaining(consumer);

        if (!spliterator.exhausted) {
            throw new Error("exhausted should be true after forEachRemaining");
        }

        // Because current was null, forEachFrom will be called with null
        // We cannot observe internal forEachFrom effects directly,
        // but exhausted flag and no exceptions indicate normal flow.
    }
}
