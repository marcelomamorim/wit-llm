/*
 * @test
 * @summary Test TreeMap.EntrySpliterator.forEachRemaining behavior including null action and concurrent modification
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Map;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormalAndConcurrentModification();
    }

    static void testForEachRemainingNullAction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");

        TreeMap.EntrySpliterator<Integer, String> spliterator = (TreeMap.EntrySpliterator<Integer, String>) map.entrySet().spliterator();

        try {
            spliterator.forEachRemaining(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testForEachRemainingNormalAndConcurrentModification() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        TreeMap.EntrySpliterator<Integer, String> spliterator = (TreeMap.EntrySpliterator<Integer, String>) map.entrySet().spliterator();

        final StringBuilder sb = new StringBuilder();
        Consumer<Map.Entry<Integer, String>> action = new Consumer<Map.Entry<Integer, String>>() {
            public void accept(Map.Entry<Integer, String> e) {
                sb.append(e.getKey()).append(':').append(e.getValue()).append(';');
            }
        };

        spliterator.forEachRemaining(action);

        String result = sb.toString();
        if (!result.contains("1:one;") || !result.contains("2:two;") || !result.contains("3:three;")) {
            throw new AssertionError("forEachRemaining did not process all entries: " + result);
        }

        // Test concurrent modification detection
        spliterator = (TreeMap.EntrySpliterator<Integer, String>) map.entrySet().spliterator();

        Consumer<Map.Entry<Integer, String>> modAction = new Consumer<Map.Entry<Integer, String>>() {
            boolean first = true;
            public void accept(Map.Entry<Integer, String> e) {
                if (first) {
                    first = false;
                    map.put(4, "four"); // structural modification
                }
            }
        };

        try {
            spliterator.forEachRemaining(modAction);
            throw new AssertionError("Expected ConcurrentModificationException due to structural modification");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
