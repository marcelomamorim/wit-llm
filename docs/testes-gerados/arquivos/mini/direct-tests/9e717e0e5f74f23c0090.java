/*
 * @test
 * @summary Test HashMap.forEachRemaining(Consumer) behavior including normal iteration and concurrent modification detection
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.HashMap.Node;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNullAction();
        testForEachRemainingConcurrentModification();
    }

    // Test normal iteration over values using forEachRemaining
    static void testForEachRemainingNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");

        // Create iterator over values
        HashMap<String, String>.ValueIterator it = map.new ValueIterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<String> action = new Consumer<String>() {
            public void accept(String v) {
                sb.append(v).append(",");
            }
        };

        it.forEachRemaining(action);

        String result = sb.toString();
        // The result should contain all values separated by commas
        if (!result.contains("alpha") || !result.contains("beta") || !result.contains("gamma")) {
            throw new AssertionError("forEachRemaining did not iterate all values: " + result);
        }
        // The string should end with a comma
        if (!result.endsWith(",")) {
            throw new AssertionError("forEachRemaining output format unexpected: " + result);
        }
    }

    // Test that forEachRemaining throws NullPointerException if action is null
    static void testForEachRemainingNullAction() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String>.ValueIterator it = map.new ValueIterator();
        try {
            it.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Test that forEachRemaining throws ConcurrentModificationException if map is modified during iteration
    static void testForEachRemainingConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "ex");
        map.put("y", "why");

        HashMap<String, String>.ValueIterator it = map.new ValueIterator();

        Consumer<String> action = new Consumer<String>() {
            public void accept(String v) {
                // Modify map during iteration to trigger CME
                map.put("z", "zee");
            }
        };

        try {
            it.forEachRemaining(action);
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
