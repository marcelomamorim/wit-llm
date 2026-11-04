/*
 * @test
 * @summary Verifies that putAll on immutable maps from Map.of/Map.ofEntries throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> src = new HashMap<>();
        src.put("a", 1);

        Map<String, Integer> empty = Map.<String, Integer>of();
        checkThrows(empty, Collections.<String, Integer>emptyMap());
        checkThrows(empty, src);
        if (empty.size() != 0) {
            throw new RuntimeException("empty map changed after putAll");
        }

        Map<String, Integer> one = Map.of("a", 1);
        checkThrows(one, src);
        checkThrows(one, Collections.<String, Integer>emptyMap());
        if (one.size() != 1 || one.get("a") != 1) {
            throw new RuntimeException("single-entry map changed after putAll");
        }

        Map<String, Integer> two = Map.ofEntries(
                Map.entry("x", 10),
                Map.entry("y", 20));
        checkThrows(two, src);
        if (two.size() != 2 || two.get("x") != 10 || two.get("y") != 20) {
            throw new RuntimeException("multi-entry map changed after putAll");
        }
        if (two.containsKey("a")) {
            throw new RuntimeException("putAll added a key to immutable map");
        }
    }

    private static void checkThrows(Map<String, Integer> target,
                                    Map<? extends String, ? extends Integer> arg) {
        try {
            target.putAll(arg);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException from putAll");
    }
}
