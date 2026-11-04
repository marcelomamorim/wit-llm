/*
 * @test    @summary Test ConcurrentHashMap constructor with initialCapacity and loadFactor
 * @run main ConcurrentHashMapWitupTest
 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithInitialCapacityAndLoadFactor();
    }

    public static void testConstructorWithInitialCapacityAndLoadFactor() {
        int initialCapacity = 16;
        float loadFactor = 0.75f;
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>(initialCapacity, loadFactor);
        if (map == null) {
            throw new AssertionError("ConcurrentHashMap instance is null");
        }
        // Verify that the map is empty after construction
        if (!map.isEmpty()) {
            throw new AssertionError("Map should be empty after construction");
        }
        // Insert an element and verify
        map.put(1, "one");
        if (map.size() != 1) {
            throw new AssertionError("Map size should be 1 after insertion");
        }
        String value = map.get(1);
        if (!"one".equals(value)) {
            throw new AssertionError("Value for key 1 should be 'one'");
        }
    }
}
