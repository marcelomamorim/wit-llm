/* @test    @summary Test the forEach method of LinkedBlockingDeque for correct iteration and null handling    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithNullConsumer();
        testForEachWithEmptyDeque();
    }

    static void testForEachWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s).append(",");
        deque.forEach(consumer);
        String result = sb.toString();
        if (!"one,two,three,".equals(result)) {
            throw new AssertionError("Expected 'one,two,three,', but got: " + result);
        }
    }

    static void testForEachWithNullConsumer() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("a");
        boolean exceptionThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null consumer");
        }
    }

    static void testForEachWithEmptyDeque() {
        LinkedBlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = i -> sb.append(i);
        deque.forEach(consumer); // should not throw
        if (sb.length() != 0) {
            throw new AssertionError("Expected empty StringBuilder, but got: " + sb.toString());
        }
    }
}
