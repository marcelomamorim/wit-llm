/*
 * @test
 * @summary Witup-style checks for ConcurrentHashMap values spliterator tryAdvance
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testNullActionOnNonEmptyMap();
        testNullActionOnEmptyMap();
        testEmptyMapReturnsFalse();
        testSingleValueDeliveredAndReturnsTrue();
    }

    static void testNullActionOnNonEmptyMap() {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        m.put("k", "v");
        Spliterator<String> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action");
        }
    }

    static void testNullActionOnEmptyMap() {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        Spliterator<String> s = m.values().spliterator();
        boolean thrown = false;
        try {
            s.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null action on empty map");
        }
    }

    static void testEmptyMapReturnsFalse() {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        Spliterator<String> s = m.values().spliterator();
        List<String> seen = new ArrayList<>();
        boolean result = s.tryAdvance(seen::add);
        if (result) {
            throw new RuntimeException("tryAdvance on empty map should return false");
        }
        if (!seen.isEmpty()) {
            throw new RuntimeException("action should not be invoked on empty map");
        }
    }

    static void testSingleValueDeliveredAndReturnsTrue() {
        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
        m.put("k", "v");
        Spliterator<String> s = m.values().spliterator();
        List<String> seen = new ArrayList<>();
        boolean first = s.tryAdvance(seen::add);
        if (!first) {
            throw new RuntimeException("first tryAdvance should return true");
        }
        if (seen.size() != 1 || !"v".equals(seen.get(0))) {
            throw new RuntimeException("action should receive the single value 'v', got " + seen);
        }
        boolean second = s.tryAdvance(seen::add);
        if (second) {
            throw new RuntimeException("second tryAdvance should return false");
        }
        if (seen.size() != 1) {
            throw new RuntimeException("action should not be invoked after exhaustion");
        }
    }
}
