/*
 * @test
 * @summary Verify that put on immutable maps created via Map.of throws UnsupportedOperationException and leaves the map unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.Map;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testPutThrowsAndPreservesContent();
        testPutOnEmptyMapThrows();
        testPutWithNullKeyThrows();
    }

    static void testPutThrowsAndPreservesContent() {
        Map<String, Integer> m = Map.of("a", 1);
        boolean threw = false;
        try {
            m.put("b", 2);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("put did not throw UnsupportedOperationException");
        }
        if (m.size() != 1 || m.get("a") != 1 || m.containsKey("b")) {
            throw new RuntimeException("map content changed after put");
        }
    }

    static void testPutOnEmptyMapThrows() {
        Map<String, Integer> m = Map.of();
        boolean threw = false;
        try {
            m.put("x", 10);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("put on empty immutable map did not throw");
        }
        if (!m.isEmpty()) {
            throw new RuntimeException("empty immutable map changed after put");
        }
    }

    static void testPutWithNullKeyThrows() {
        Map<String, Integer> m = Map.of("a", 1);
        boolean threw = false;
        try {
            m.put(null, 2);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("put with null key did not throw UnsupportedOperationException");
        }
    }
}
