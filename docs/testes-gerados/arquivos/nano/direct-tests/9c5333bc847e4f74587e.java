/* @test    @summary Test WeakHashMap constructor with initial capacity    @run main WeakHashMapWitupTest */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {

    public static void main(String[] args) {
        testConstructorWithInitialCapacity();
    }

    public static void testConstructorWithInitialCapacity() {
        int initialCapacity = 10;
        WeakHashMap<Object, Object> map = new WeakHashMap<>(initialCapacity);
        // Verify that the map is initialized and empty
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0, but got " + map.size());
        }
        // Insert an entry and verify
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);
        if (map.size() != 1) {
            throw new AssertionError("Expected size 1 after put, but got " + map.size());
        }
        if (map.get(key) != value) {
            throw new AssertionError("Value retrieved does not match the inserted value");
        }
        // Remove the entry and verify
        map.remove(key);
        if (map.size() != 0) {
            throw new AssertionError("Expected size 0 after removal, but got " + map.size());
        }
    }
}
