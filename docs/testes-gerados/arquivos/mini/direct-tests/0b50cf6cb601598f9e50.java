/*
 * @test
 * @summary Test ArrayBlockingQueue.forEach with various Consumers and queue states
 * @run main ArrayBlockingQueueWitupTest
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.Objects;

public class ArrayBlockingQueueWitupTest {

    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithEmptyQueue();
        testForEachWithSingleElement();
        testForEachWithMultipleElements();
        testForEachWithConsumerThatThrows();
    }

    static void testForEachWithNullConsumer() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        try {
            q.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithEmptyQueue() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        final StringBuilder sb = new StringBuilder();
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (sb.length() != 0) {
            throw new RuntimeException("forEach on empty queue should not call accept");
        }
    }

    static void testForEachWithSingleElement() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("one");
        final StringBuilder sb = new StringBuilder();
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!"one".equals(sb.toString())) {
            throw new RuntimeException("forEach did not process single element correctly");
        }
    }

    static void testForEachWithMultipleElements() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("one");
        q.add("two");
        q.add("three");
        final StringBuilder sb = new StringBuilder();
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.equals("one,two,three,")) {
            throw new RuntimeException("forEach did not process multiple elements correctly: " + result);
        }
    }

    static void testForEachWithConsumerThatThrows() {
        ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(10);
        q.add("one");
        try {
            q.forEach(new Consumer<String>() {
                public void accept(String s) {
                    throw new RuntimeException("Consumer exception");
                }
            });
            throw new RuntimeException("Expected RuntimeException from consumer");
        } catch (RuntimeException e) {
            if (!"Consumer exception".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

}
