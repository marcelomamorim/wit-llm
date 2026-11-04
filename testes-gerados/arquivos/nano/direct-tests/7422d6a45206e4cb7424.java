/*
 * @test
 * @summary Tests the forEach method of ArrayDeque for correct iteration behavior.
 * @run main ArrayDequeWitupTest
 */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmpty();
        testForEachWithNullConsumer();
    }

    static void testForEachWithElements() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        deque.add(3);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> consumer = (Integer e) -> sb.append(e);
        deque.forEach(consumer);
        assert sb.toString().equals("123") : "Expected concatenation of elements to be '123', but was '" + sb.toString() + "'";
    }

    static void testForEachWithEmpty() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = (String e) -> sb.append(e);
        deque.forEach(consumer);
        assert sb.toString().isEmpty() : "Expected empty string for empty deque";
    }

    static void testForEachWithNullConsumer() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        boolean exceptionThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when consumer is null";
    }
}
