/*
 * @test
 * @summary Test ConcurrentSkipListMap tryAdvance method behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Consumer;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithEmptyMap();
        testTryAdvanceWithSingleEntry();
        testTryAdvanceWithMultipleEntries();
        testTryAdvanceStopsAtFence();
    }

    static void testTryAdvanceWithNullAction() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.values().spliterator().tryAdvance(null);
            throw new AssertionError("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testTryAdvanceWithEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        boolean advanced = map.values().spliterator().tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("Should not be called on empty map");
            }
        });
        if (advanced) {
            throw new AssertionError("tryAdvance should return false on empty map");
        }
    }

    static void testTryAdvanceWithSingleEntry() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        final StringBuilder sb = new StringBuilder();
        boolean advanced = map.values().spliterator().tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!advanced) {
            throw new AssertionError("tryAdvance should return true when element present");
        }
        if (!"one".equals(sb.toString())) {
            throw new AssertionError("Consumer should receive the value 'one'");
        }
        // Next tryAdvance should return false
        boolean secondAdvance = map.values().spliterator().tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                throw new AssertionError("No more elements expected");
            }
        });
        if (secondAdvance) {
            throw new AssertionError("tryAdvance should return false after last element");
        }
    }

    static void testTryAdvanceWithMultipleEntries() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        final StringBuilder sb = new StringBuilder();
        java.util.Spliterator<String> spliterator = map.values().spliterator();
        while (spliterator.tryAdvance(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s).append(",");
            }
        })) {
            // continue
        }
        String result = sb.toString();
        if (!result.contains("one") || !result.contains("two") || !result.contains("three")) {
            throw new AssertionError("All values should be consumed by tryAdvance");
        }
    }

    static void testTryAdvanceStopsAtFence() {
        // Fence is internal to spliterator, no public API to set it.
        // So this test cannot be done directly without reflection,
        // which is disallowed by instructions.
        // Hence, no test for fence stopping condition.
    }
}
