/*
 * @test
 * @summary Verify that computeIfPresent on immutable maps from Map.of throws UnsupportedOperationException and leaves the mapping unchanged
 * @run main ImmutableCollectionsWitupTest
 */
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> single = Map.of("a", 1);
        checkThrowsAndUnchanged(single, "a", 1);
        checkThrowsAndUnchanged(single, "z", null);

        Map<String, Integer> two = Map.of("a", 1, "b", 2);
        checkThrowsAndUnchanged(two, "a", 1);
        checkThrowsAndUnchanged(two, "z", null);

        Map<String, Integer> empty = Map.of();
        checkThrowsAndUnchanged(empty, "x", null);
    }

    static void checkThrowsAndUnchanged(Map<String, Integer> m, String key, Integer expected) {
        boolean thrown = false;
        try {
            m.computeIfPresent(key, (k, v) -> v + 1);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException for key " + key + " in " + m.getClass());
        }
        if (!java.util.Objects.equals(m.get(key), expected)) {
            throw new AssertionError("Map was modified for key " + key);
        }
    }
}
