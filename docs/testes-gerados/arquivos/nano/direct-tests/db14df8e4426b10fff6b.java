/*
 * @test    @summary Test for ConcurrentLinkedDeque forEach method
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

public class ConcurrentLinkedDequeWitupTest {

    public static void main(String[] args) {
        testForEachWithElements();
        testForEachWithEmptyDeque();
    }

    static void testForEachWithElements() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        deque.add("a");
        deque.add("b");
        deque.add("c");
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        deque.forEach(consumer);
        String expected1 = "abc";
        if (!result.toString().equals(expected1)) {
            throw new AssertionError("Expected 'abc' but got '" + result.toString() + "'");
        }
    }

    static void testForEachWithEmptyDeque() {
        ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();
        StringBuilder result = new StringBuilder();
        Consumer<String> consumer = s -> result.append(s);
        deque.forEach(consumer);
        String expected2 = "";
        if (!result.toString().equals(expected2)) {
            throw new AssertionError("Expected empty string but got '" + result.toString() + "'");
        }
    }
}
