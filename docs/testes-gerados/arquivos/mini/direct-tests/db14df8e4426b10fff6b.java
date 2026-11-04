/*
 * @test
 * @summary Test ConcurrentLinkedDeque.forEach with various Consumers and inputs
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.Objects;

public class ConcurrentLinkedDequeWitupTest {
    public static void main(String[] args) {
        testForEachWithNullConsumer();
        testForEachWithEmptyDeque();
        testForEachWithNonEmptyDeque();
        testForEachWithConsumerThrowingException();
    }

    static void testForEachWithNullConsumer() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        try {
            deque.forEach(null);
            throw new RuntimeException("Expected NullPointerException for null Consumer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        final boolean[] called = {false};
        deque.forEach(new Consumer<String>() {
            public void accept(String s) {
                called[0] = true;
            }
        });
        if (called[0]) {
            throw new RuntimeException("Consumer should not be called on empty deque");
        }
    }

    static void testForEachWithNonEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        final StringBuilder sb = new StringBuilder();
        deque.forEach(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        // The order is not strictly guaranteed but for ConcurrentLinkedDeque it is FIFO order
        if (!"abc".equals(result)) {
            throw new RuntimeException("Expected concatenation 'abc' but got: " + result);
        }
    }

    static void testForEachWithConsumerThrowingException() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("x");
        deque.add("y");

        final RuntimeException ex = new RuntimeException("fail");
        Consumer<String> throwingConsumer = new Consumer<String>() {
            public void accept(String s) {
                throw ex;
            }
        };

        try {
            deque.forEach(throwingConsumer);
            throw new RuntimeException("Expected RuntimeException from Consumer.accept");
        } catch (RuntimeException e) {
            if (e != ex) {
                throw new RuntimeException("Unexpected exception thrown", e);
            }
            // expected
        }
    }
}
