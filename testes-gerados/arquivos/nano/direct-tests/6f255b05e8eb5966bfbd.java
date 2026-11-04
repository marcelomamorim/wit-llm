/* @test    @summary Test for LinkedBlockingDeque.forEachRemaining method    @run main LinkedBlockingDequeWitupTest */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyDeque();
        testForEachRemainingNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        deque.forEachRemaining(consumer);
        assert result.toString().equals("one,two,three,") : "Failed: Expected 'one,two,three,' but got '" + result.toString() + "'";
    }

    static void testForEachRemainingWithEmptyDeque() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s).append(",");
        deque.forEachRemaining(consumer);
        assert result.toString().equals("") : "Failed: Expected empty string but got '" + result.toString() + "'";
    }

    static void testForEachRemainingNullConsumer() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("test");
        boolean exceptionThrown = false;
        try {
            deque.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Failed: Expected NullPointerException when passing null consumer";
    }
}
