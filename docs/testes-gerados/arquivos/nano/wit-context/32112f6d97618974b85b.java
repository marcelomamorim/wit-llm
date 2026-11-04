/* @test    @summary Test for ArrayDeque.forEachRemaining with null Consumer    @run main ArrayDequeWitupTest */

import java.util.ArrayDeque;
import java.util.function.Consumer;

public class ArrayDequeWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    static void testForEachRemainingNullAction() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(1);
        deque.add(2);
        boolean exceptionThrown = false;
        try {
            deque.iterator().forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEachRemaining");
        }
    }

    static void testForEachRemainingNormal() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("a");
        deque.add("b");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        deque.iterator().forEachRemaining(consumer);
        String result = sb.toString();
        if (!"ab".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
