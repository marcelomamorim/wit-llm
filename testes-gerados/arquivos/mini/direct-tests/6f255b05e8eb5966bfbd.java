/*
 * @test
 * @summary Test LinkedBlockingDeque.forEachRemaining(Consumer) method
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingOnEmptyDeque();
        testForEachRemainingOnNonEmptyDeque();
    }

    static void testForEachRemainingWithNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        try {
            deque.iterator().forEachRemaining(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingOnEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        final AtomicInteger count = new AtomicInteger(0);
        deque.iterator().forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != 0) {
            throw new RuntimeException("forEachRemaining should not invoke action on empty deque");
        }
    }

    static void testForEachRemainingOnNonEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");

        final StringBuilder sb = new StringBuilder();
        deque.iterator().forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!"abc".equals(result)) {
            throw new RuntimeException("forEachRemaining did not process all elements in order: " + result);
        }

        // Subsequent call to forEachRemaining should do nothing
        final AtomicInteger count = new AtomicInteger(0);
        java.util.Iterator<String> it = deque.iterator();
        it.next(); // advance one element
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        int countAfterFirst = count.get();
        it.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                count.incrementAndGet();
            }
        });
        if (count.get() != countAfterFirst) {
            throw new RuntimeException("forEachRemaining should not invoke action after exhausted");
        }
    }
}
