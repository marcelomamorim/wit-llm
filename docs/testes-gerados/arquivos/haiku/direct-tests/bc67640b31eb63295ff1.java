/*
 * @test
 * @summary Verify get() of immutable maps returns mapped values, null for absent keys, and throws NPE for null on an empty map
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;
import java.util.Objects;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> m = Map.of("a", 1, "b", 2, "c", 3);

        assertEquals(Integer.valueOf(1), m.get("a"));
        assertEquals(Integer.valueOf(2), m.get("b"));
        assertEquals(Integer.valueOf(3), m.get("c"));
        assertEquals(null, m.get("z"));
        assertEquals(null, m.get(Integer.valueOf(1)));

        Map<String, Integer> empty = Map.of();
        assertEquals(null, empty.get("x"));

        boolean thrown = false;
        try {
            empty.get(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for get(null) on empty immutable map");
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
