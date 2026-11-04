/*
 * @test
 * @summary Test for ArrayList's forEachRemaining method to verify behavior with null Consumer.
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Consumer;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullConsumer();
        testForEachRemainingWithValidConsumer();
    }

    public static void testForEachRemainingWithNullConsumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.forEachRemaining(null);
            // If no exception, test fails
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    public static void testForEachRemainingWithValidConsumer() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = s -> sb.append(s);
        list.forEachRemaining(consumer);
        String result = sb.toString();
        if (!"xy".equals(result)) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }
}
