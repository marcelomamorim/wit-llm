/*
 * @test
 * @summary Verify that computeIfPresent on immutable maps from Map.of and Map.ofEntries always throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        Map<String, Integer> empty = Map.of();
        Map<String, Integer> one = Map.of("a", 1);
        Map<String, Integer> two = Map.of("a", 1, "b", 2);
        Map<String, Integer> entries = Map.ofEntries(Map.entry("a", 1), Map.entry("b", 2), Map.entry("c", 3));

        expectUoe(() -> empty.computeIfPresent("a", (k, v) -> v + 1), "empty map, absent key");
        expectUoe(() -> one.computeIfPresent("a", (k, v) -> v + 1), "single entry, present key");
        expectUoe(() -> one.computeIfPresent("z", (k, v) -> v + 1), "single entry, absent key");
        expectUoe(() -> two.computeIfPresent("b", (k, v) -> v * 10), "two entries, present key");
        expectUoe(() -> entries.computeIfPresent("c", (k, v) -> null), "multi entry, remapping to null");
        expectUoe(() -> one.computeIfPresent("a", null), "null remapping function");

        if (one.get("a") != 1 || two.get("b") != 2 || entries.get("c") != 3) {
            throw new RuntimeException("immutable map contents changed after computeIfPresent");
        }
    }

    private static void expectUoe(Runnable action, String description) {
        try {
            action.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException: " + description);
    }
}
