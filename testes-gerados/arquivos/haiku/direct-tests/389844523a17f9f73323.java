/*
 * @test
 * @summary Verify that replace(K, V) on immutable maps from Map.of and Map.copyOf throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.HashMap;
import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceOnEmptyMapThrows();
        testReplaceExistingKeyThrowsAndKeepsValue();
        testReplaceAbsentKeyThrows();
        testReplaceWithNullValueThrows();
        testReplaceOnCopyOfThrows();
    }

    static void testReplaceOnEmptyMapThrows() {
        Map<String, Integer> m = Map.of();
        expectUOE("empty map replace", () -> m.replace("a", 1));
        check(m.isEmpty(), "empty map must remain empty");
    }

    static void testReplaceExistingKeyThrowsAndKeepsValue() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUOE("existing key replace", () -> m.replace("a", 2));
        check(m.get("a") == 1, "value must remain 1 after failed replace");
        check(m.size() == 1, "size must remain 1");
    }

    static void testReplaceAbsentKeyThrows() {
        Map<String, Integer> m = Map.of("a", 1);
        expectUOE("absent key replace", () -> m.replace("missing", 2));
        check(!m.containsKey("missing"), "absent key must not be added");
    }

    static void testReplaceWithNullValueThrows() {
        Map<String, Integer> m = Map.of("a", 1, "b", 2);
        expectUOE("null value replace", () -> m.replace("b", null));
        check(m.get("b") == 2, "value for b must remain 2");
    }

    static void testReplaceOnCopyOfThrows() {
        HashMap<String, Integer> src = new HashMap<>();
        src.put("x", 10);
        Map<String, Integer> m = Map.copyOf(src);
        expectUOE("copyOf replace", () -> m.replace("x", 20));
        check(m.get("x") == 10, "copyOf value must remain 10");
    }

    static void expectUOE(String name, Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new AssertionError(name + ": expected UnsupportedOperationException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new AssertionError(msg);
        }
    }
}
