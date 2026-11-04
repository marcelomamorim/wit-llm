/*
 * @test
 * @summary Test LinkedBlockingDeque.forEach(Consumer) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachOnEmptyDeque();
        testForEachOnNonEmptyDeque();
        testForEachWithConsumerThrowingException();
    }

    static void testForEachWithNullConsumer() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicInteger count = new AtomicInteger(0);
        deque.forEach(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new RuntimeException("forEach on empty deque should not invoke consumer");
        }
    }

    static void testForEachOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        final StringBuilder sb = new StringBuilder();
        deque.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        });
        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new RuntimeException("forEach did not visit all elements: " + result);
        }
    }

    static void testForEachWithConsumerThrowingException() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("element");
        final RuntimeException ex = new RuntimeException("fail");
        try {
            deque.forEach(new Consumer<String>() {
                public void accept(String s) {
                    throw ex;
                }
            });
            throw new RuntimeException("Expected RuntimeException from consumer");
        } catch (RuntimeException e) {
            if (e != ex) {
                throw new RuntimeException("Unexpected exception thrown", e);
            }
        }
    }
}
