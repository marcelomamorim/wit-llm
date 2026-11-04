/*
 * @test
 * @summary Test java.util.concurrent.ArrayBlockingQueue.forEach method for null action and normal iteration
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormalIteration();
    }

    static void testForEachNullAction() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
        try {
            queue.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    static void testForEachNormalIteration() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.add("one");
        queue.add("two");
        queue.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        queue.forEach(action);

        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new RuntimeException("forEach did not iterate correctly, got: " + result);
        }
    }
}
