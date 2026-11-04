/*
 * @test
 * @summary Verifies containsKey behavior of immutable maps created by Map.of (ImmutableCollections.MapN)
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testEmptyMap();
        testSingleEntryMap();
        testMultiEntryMap();
        testNullKeyThrows();
    }

    static void testEmptyMap() {
        Map<String, Integer> empty = Map.of();
        check(!empty.containsKey("a"), "empty map must not contain \"a\"");
        check(!empty.containsKey(""), "empty map must not contain empty string");
    }

    static void testSingleEntryMap() {
        Map<String, Integer> one = Map.of("a", 1);
        check(one.containsKey("a"), "single-entry map must contain its key");
        check(!one.containsKey("b"), "single-entry map must not contain other key");
    }

    static void testMultiEntryMap() {
        Map<String, Integer> three = Map.of("a", 1, "b", 2, "c", 3);
        check(three.containsKey("a"), "multi-entry map must contain \"a\"");
        check(three.containsKey("b"), "multi-entry map must contain \"b\"");
        check(three.containsKey("c"), "multi-entry map must contain \"c\"");
        check(!three.containsKey("d"), "multi-entry map must not contain \"d\"");
        check(!three.containsKey(Integer.valueOf(1)), "key of different type must not match");
    }

    static void testNullKeyThrows() {
        Map<String, Integer> empty = Map.of();
        boolean thrown = false;
        try {
            empty.containsKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsKey(null) on empty immutable map must throw NullPointerException");

        Map<String, Integer> three = Map.of("a", 1, "b", 2, "c", 3);
        thrown = false;
        try {
            three.containsKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "containsKey(null) on multi-entry immutable map must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
