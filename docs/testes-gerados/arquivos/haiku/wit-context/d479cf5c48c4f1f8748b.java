/*
 * @test
 * @summary Verify that compute on immutable maps created via Map.of throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        expectUoe(() -> Map.of("a", 1).compute("a", (k, v) -> v + 1), "Map1 compute existing key");
        expectUoe(() -> Map.of("a", 1).compute("z", (k, v) -> 2), "Map1 compute absent key");
        expectUoe(() -> Map.<String, Integer>of().compute("a", (k, v) -> 1), "empty map compute");
        expectUoe(() -> Map.of("a", 1, "b", 2, "c", 3).compute("b", (k, v) -> v * 2), "MapN compute existing key");
        expectUoe(() -> Map.of("a", 1, "b", 2, "c", 3).compute("z", (k, v) -> 4), "MapN compute absent key");
    }

    static void expectUoe(Runnable r, String description) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException: " + description);
    }
}
