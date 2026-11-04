/*
 * @test
 * @summary Test for ArrayList's forEachRemaining method to ensure it correctly applies the Consumer to remaining elements.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmpty();
        testForEachRemainingWithNullConsumer();
    }

    static void testForEachRemainingWithElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        list.forEachRemaining(consumer);
        assert sb.toString().equals("abc") : "Expected 'abc', got '" + sb.toString() + "'";
    }

    static void testForEachRemainingWithEmpty() {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        list.forEachRemaining(consumer);
        assert sb.toString().isEmpty() : "Expected empty string, got '" + sb.toString() + "'";
    }

    static void testForEachRemainingWithNullConsumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        boolean exceptionThrown = false;
        try {
            list.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException when consumer is null";
    }
}
