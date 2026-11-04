/*
 * @test
 * @summary Verifies Map.of() get() behavior for the empty MapN path (null key NPE, missing key null) and a non-empty MapN lookup
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> empty = Map.of();
        boolean npe = false;
        try {
            empty.get(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("Expected NullPointerException for get(null) on empty immutable map");
        }
        if (empty.get("x") != null) {
            throw new RuntimeException("Expected null for missing key on empty immutable map");
        }

        Map<String, Integer> three = Map.of("a", 1, "b", 2, "c", 3);
        Integer a = three.get("a");
        if (a == null || a.intValue() != 1) {
            throw new RuntimeException("Expected value 1 for key a, got " + a);
        }
        if (three.get("z") != null) {
            throw new RuntimeException("Expected null for missing key z");
        }
    }
}
