/* @test    @summary Test for HashMap forEach method handling null action    @run main HashMapWitupTest */

import java.util.HashMap;
import java.util.function.Consumer;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithEmptyMap();
        testForEachWithSingleEntry();
    }

    private static void testForEachWithNullAction() {
        HashMap<String, Integer> map = new HashMap<>();
        boolean exceptionThrown = false;
        try {
            map.forEach(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when action is null");
        }
    }

    private static void testForEachWithEmptyMap() {
        HashMap<String, Integer> map = new HashMap<>();
        final boolean[] called = {false};
        map.forEach(k -> {
            called[0] = true;
        });
        if (called[0]) {
            throw new AssertionError("Action should not be called for empty map");
        }
    }

    private static void testForEachWithSingleEntry() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        final StringBuilder sb = new StringBuilder();
        map.forEach(k -> sb.append(k));
        if (!"key".equals(sb.toString())) {
            throw new AssertionError("Action did not process the key correctly");
        }
    }
}
