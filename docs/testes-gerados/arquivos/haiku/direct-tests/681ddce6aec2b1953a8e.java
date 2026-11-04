/*
 * @test
 * @summary merge on immutable maps must throw UnsupportedOperationException without invoking the remapping function or changing the map
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testMergeExistingKeyThrows();
        testMergeAbsentKeyThrows();
        testMergeOnEmptyMapThrows();
        System.out.println("PASSED");
    }

    static void testMergeExistingKeyThrows() {
        Map<String, Integer> m = Map.of("a", 1);
        AtomicInteger calls = new AtomicInteger();
        boolean thrown = false;
        try {
            m.merge("a", 2, (x, y) -> { calls.incrementAndGet(); return x + y; });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException");
        }
        if (calls.get() != 0) {
            throw new RuntimeException("remapping function must not be invoked");
        }
        if (m.get("a") != 1) {
            throw new RuntimeException("map must remain unchanged");
        }
    }

    static void testMergeAbsentKeyThrows() {
        Map<String, Integer> m = Map.of("a", 1);
        AtomicInteger calls = new AtomicInteger();
        boolean thrown = false;
        try {
            m.merge("b", 5, (x, y) -> { calls.incrementAndGet(); return x + y; });
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException");
        }
        if (calls.get() != 0) {
            throw new RuntimeException("remapping function must not be invoked");
        }
        if (m.containsKey("b") || m.size() != 1) {
            throw new RuntimeException("map must not gain the absent key");
        }
    }

    static void testMergeOnEmptyMapThrows() {
        Map<String, Integer> m = Map.of();
        boolean thrown = false;
        try {
            m.merge("x", 1, Integer::sum);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected UnsupportedOperationException on empty immutable map");
        }
        if (!m.isEmpty()) {
            throw new RuntimeException("empty map must remain empty");
        }
    }
}
