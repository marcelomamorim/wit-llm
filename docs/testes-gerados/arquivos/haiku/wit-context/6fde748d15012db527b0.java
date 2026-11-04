/*
 * @test
 * @summary Verify that computeIfAbsent on an unmodifiable map view from Collections throws UnsupportedOperationException and leaves the map unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> base = new HashMap<>();
        base.put("a", 1);
        Map<String, Integer> unmodifiable = Collections.unmodifiableMap(base);
        Function<String, Integer> mapper = k -> 2;

        try {
            unmodifiable.computeIfAbsent("b", mapper);
            throw new RuntimeException("expected UnsupportedOperationException for absent key");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            unmodifiable.computeIfAbsent("a", mapper);
            throw new RuntimeException("expected UnsupportedOperationException for present key");
        } catch (UnsupportedOperationException expected) {
        }

        try {
            unmodifiable.computeIfAbsent(null, mapper);
            throw new RuntimeException("expected UnsupportedOperationException for null key");
        } catch (UnsupportedOperationException expected) {
        }

        if (unmodifiable.size() != 1 || unmodifiable.containsKey("b")
                || unmodifiable.get("a") != 1) {
            throw new RuntimeException("unmodifiable map was changed");
        }
    }
}
