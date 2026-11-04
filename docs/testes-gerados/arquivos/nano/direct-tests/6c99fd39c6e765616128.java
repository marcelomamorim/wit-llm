/* @test    @summary Test for forEachRemaining method in ConcurrentSkipListMap    @run main ConcurrentSkipListMapWitupTest */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithElements();
        testForEachRemainingWithEmptyMap();
        testForEachRemainingWithNullAction();
    }

    static void testForEachRemainingWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = v -> sb.append(v).append(",");
        // Obtain iterator over values
        var iterator = map.values().iterator();
        // Advance iterator to simulate partial iteration
        if (iterator.hasNext()) {
            iterator.next();
        }
        // Use forEachRemaining to process remaining elements
        iterator.forEachRemaining(consumer);
        String result = sb.toString();
        // The remaining elements should be "two,three," in some order
        boolean condition = result.contains("two,") && result.contains("three,");
        if (!condition) {
            throw new AssertionError("forEachRemaining did not process remaining elements correctly: " + result);
        }
    }

    static void testForEachRemainingWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        var iterator = map.values().iterator();
        StringBuilder sb = new StringBuilder();
        Consumer<String> consumer = v -> sb.append(v).append(",");
        iterator.forEachRemaining(consumer);
        if (sb.length() != 0) {
            throw new AssertionError("Expected no elements to process, but got: " + sb.toString());
        }
    }

    static void testForEachRemainingWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        var iterator = map.values().iterator();
        boolean exceptionThrown = false;
        try {
            iterator.forEachRemaining(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null to forEachRemaining");
        }
    }
}
