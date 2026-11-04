/*
 * @test
 * @summary Test HashMap.ValueSpliterator.forEachRemaining behavior including NPE on null action
 * @run main HashMapWitupTest
 */

import java.util.HashMap;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingNullAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        // Create ValueSpliterator from map.values().spliterator()
        java.util.Spliterator<String> spliterator = map.values().spliterator();

        try {
            // forEachRemaining with null action should throw NullPointerException
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormal() {
        HashMap<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");

        java.util.Spliterator<String> spliterator = map.values().spliterator();

        final StringBuilder sb = new StringBuilder();
        spliterator.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });

        String result = sb.toString();
        if (!(result.contains("1") && result.contains("2"))) {
            throw new AssertionError("forEachRemaining did not process all values: " + result);
        }
    }

    static void testForEachRemainingConcurrentModification() {
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "X");

        java.util.Spliterator<String> spliterator = map.values().spliterator();

        // Modify map after spliterator creation to trigger ConcurrentModificationException
        map.put("y", "Y");

        try {
            spliterator.forEachRemaining(new Consumer<String>() {
                public void accept(String s) {
                    // no-op
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException due to modification during iteration");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
