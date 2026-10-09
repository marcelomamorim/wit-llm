/* @test    @summary Test HashMap forEach method for correct traversal and behavior    @run main HashMapWitupTest_P7e2c57f8b447 */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest_P7e2c57f8b447 {

    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachNullAction();
    }

    static void testForEachWithEntries() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        final StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> {
            result.append(k).append(v);
        });
        String combined = result.toString();
        boolean hasA1 = combined.contains("a1");
        boolean hasB2 = combined.contains("b2");
        boolean hasC3 = combined.contains("c3");
        if (!(hasA1 && hasB2 && hasC3)) {
            throw new AssertionError("forEach did not process all entries correctly: " + combined);
        }
    }

    static void testForEachWithEmptyMap() {
        HashMap<String, String> emptyMap = new HashMap<>();
        final boolean[] called = {false};
        emptyMap.forEach((k, v) -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("forEach should not call action on empty map");
        }
    }

    static void testForEachNullAction() {
        HashMap<String, String> map = new HashMap<>();
        map.put("x", "y");
        try {
            map.forEach(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
