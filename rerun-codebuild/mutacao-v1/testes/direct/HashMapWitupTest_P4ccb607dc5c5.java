/* @test    @summary Test HashMap forEach method with normal entries    @run main HashMapWitupTest_P4ccb607dc5c5 */

import java.util.HashMap;
import java.util.function.BiConsumer;

public class HashMapWitupTest_P4ccb607dc5c5 {
    public static void main(String[] args) {
        testForEachWithEntries();
        testForEachWithEmptyMap();
        testForEachWithNullAction();
    }

    static void testForEachWithEntries() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        StringBuilder result = new StringBuilder();
        map.forEach((k, v) -> result.append(k).append(v));
        String combined = result.toString();
        if (!(combined.contains("a1") && combined.contains("b2") && combined.contains("c3"))) {
            throw new AssertionError("Failed: forEach did not process all entries correctly. Result: " + combined);
        }
    }

    static void testForEachWithEmptyMap() {
        HashMap<String, String> emptyMap = new HashMap<>();
        final boolean[] called = {false};
        emptyMap.forEach((k, v) -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("Failed: forEach should not call action on empty map.");
        }
    }

    static void testForEachWithNullAction() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Failed: forEach with null action should throw NullPointerException.");
        }
    }
}
