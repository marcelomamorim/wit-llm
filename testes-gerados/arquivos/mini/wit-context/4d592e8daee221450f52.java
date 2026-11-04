/*
 * @test
 * @summary Test java.util.Arrays.ArrayList.forEach method for null action and normal iteration
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.Objects;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testForEachNullAction();
        testForEachNormal();
        System.out.println("PASS");
    }

    private static void testForEachNullAction() {
        // Create an Arrays.ArrayList instance via Arrays.asList
        java.util.List<String> list = Arrays.asList("a", "b", "c");

        try {
            list.forEach(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachNormal() {
        java.util.List<String> list = Arrays.asList("x", "y", "z");
        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };
        list.forEach(action);
        String result = sb.toString();
        if (!"xyz".equals(result)) {
            throw new RuntimeException("forEach did not iterate correctly, got: " + result);
        }
    }
}
