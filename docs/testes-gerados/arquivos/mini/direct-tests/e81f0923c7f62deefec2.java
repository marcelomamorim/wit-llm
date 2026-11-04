/*
 * @test
 * @summary Test ConcurrentLinkedQueue.forEach with various Consumers
 * @run main ConcurrentLinkedQueueWitupTest
 */

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ConcurrentLinkedQueueWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithEmptyQueue();
        testForEachWithSingleElement();
        testForEachWithMultipleElements();
        testForEachConsumerThrowsException();
    }

    static void testForEachWithNullConsumer() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        try {
            q.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithEmptyQueue() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        final AtomicInteger count = new AtomicInteger(0);
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new RuntimeException("forEach on empty queue should not invoke consumer");
        }
    }

    static void testForEachWithSingleElement() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("one");
        final AtomicInteger count = new AtomicInteger(0);
        final StringBuilder sb = new StringBuilder();
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
                sb.append(s);
            }
        });
        if (count.get() != 1 || !"one".equals(sb.toString())) {
            throw new RuntimeException("forEach did not process single element correctly");
        }
    }

    static void testForEachWithMultipleElements() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("a");
        q.add("b");
        q.add("c");
        final StringBuilder sb = new StringBuilder();
        q.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        String result = sb.toString();
        if (!result.contains("a") || !result.contains("b") || !result.contains("c")) {
            throw new RuntimeException("forEach did not process all elements");
        }
        if (result.length() != 3) {
            throw new RuntimeException("forEach processed incorrect number of elements");
        }
    }

    static void testForEachConsumerThrowsException() {
        ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<>();
        q.add("x");
        final RuntimeException ex = new RuntimeException("fail");
        try {
            q.forEach(new Consumer<String>() {
                public void accept(String s) {
                    throw ex;
                }
            });
            throw new RuntimeException("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (e != ex) {
                throw new RuntimeException("Unexpected exception thrown", e);
            }
        }
    }
}
