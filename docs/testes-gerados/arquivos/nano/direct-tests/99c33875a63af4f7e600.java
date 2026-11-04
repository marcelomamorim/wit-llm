/*
 * @test
 * @summary Test forEach method of ConcurrentSkipListMap to ensure it correctly applies action to all entries.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachNullAction();
    }

    static void testForEachWithEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> action = (k, v) -> result.append(k).append(':').append(v).append(';');
        map.forEach(action);
        String output = result.toString();
        boolean condition = output.contains("1:one;") && output.contains("2:two;") && output.contains("3:three;");
        if (!condition) {
            throw new AssertionError("forEach did not process all entries correctly: " + output);
        }
    }

    static void testForEachWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        StringBuilder result = new StringBuilder();
        BiConsumer<Integer, String> action = (k, v) -> result.append(k).append(':').append(v).append(';');
        map.forEach(action);
        if (result.length() != 0) {
            throw new AssertionError("forEach on empty map should not invoke action, but got: " + result.toString());
        }
    }

    static void testForEachNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null action to forEach");
        }
    }
}
