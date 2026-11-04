/*
 * @test
 * @summary Test LinkedBlockingDeque.forEach method for null argument and normal usage
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.Objects;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
    }

    private static void testForEachNullAction() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        boolean npeThrown = false;
        try {
            deque.forEach(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEach");
        }
    }

    private static void testForEachNormal() {
        LinkedBlockingDeque<String> deque = new LinkedBlockingDeque<>();
        deque.add("one");
        deque.add("two");
        deque.add("three");

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        };

        deque.forEach(action);

        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("forEach did not process all elements: " + result);
        }
    }
}
