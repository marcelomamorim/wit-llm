/*
 * @test
 * @summary computeIfAbsent on immutable maps always throws UnsupportedOperationException without invoking the mapping function
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        expectUoe(Map.<String, Integer>of(), "a");
        expectUoe(Map.of("a", 1), "a");
        expectUoe(Map.of("a", 1), "b");
        expectUoe(Map.of("a", 1, "b", 2, "c", 3), "d");
        expectUoe(Map.ofEntries(Map.entry("x", 10)), "y");
    }

    static void expectUoe(Map<String, Integer> map, String key) {
        AtomicInteger calls = new AtomicInteger();
        Function<String, Integer> mf = k -> {
            calls.incrementAndGet();
            return 42;
        };
        boolean thrown = false;
        try {
            map.computeIfAbsent(key, mf);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for key " + key);
        }
        if (calls.get() != 0) {
            throw new RuntimeException("Mapping function must not be invoked, calls=" + calls.get());
        }
    }
}
