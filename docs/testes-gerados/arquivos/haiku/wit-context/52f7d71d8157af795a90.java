/*
 * @test
 * @summary Verify that UnmodifiableMap.putAll (via Collections.unmodifiableMap) always throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testPutAllEmptySourceThrowsUOE();
        testPutAllNonEmptySourceThrowsUOE();
        testPutAllDoesNotModifyTarget();
    }

    static void testPutAllEmptySourceThrowsUOE() {
        Map<String, Integer> target = new HashMap<>();
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(target);
        try {
            unmodifiable.putAll(new HashMap<String, Integer>());
            throw new RuntimeException("Expected UnsupportedOperationException for empty source");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testPutAllNonEmptySourceThrowsUOE() {
        Map<String, Integer> target = new HashMap<>();
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(target);
        Map<String, Integer> source = new HashMap<>();
        source.put("a", 1);
        source.put("b", 2);
        try {
            unmodifiable.putAll(source);
            throw new RuntimeException("Expected UnsupportedOperationException for non-empty source");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    static void testPutAllDoesNotModifyTarget() {
        Map<String, Integer> target = new HashMap<>();
        target.put("x", 10);
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(target);
        Map<String, Integer> source = new HashMap<>();
        source.put("y", 20);
        try {
            unmodifiable.putAll(source);
            throw new RuntimeException("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (unmodifiable.size() != 1 || !Integer.valueOf(10).equals(unmodifiable.get("x"))
                || unmodifiable.containsKey("y")) {
            throw new RuntimeException("Unmodifiable map was modified by putAll");
        }
    }
}
