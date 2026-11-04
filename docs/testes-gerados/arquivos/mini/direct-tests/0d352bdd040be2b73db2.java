/*
 * @test
 * @summary Test default constructor of WeakHashMap
 * @run main WeakHashMapWitupTest
 */

import java.util.WeakHashMap;

public class WeakHashMapWitupTest {
    public static void main(String[] args) {
        WeakHashMap<Object, Object> map = new WeakHashMap<>();
        if (map == null) throw new RuntimeException("WeakHashMap constructor returned null");
        if (map.size() != 0) throw new RuntimeException("New WeakHashMap is not empty");
        if (!map.isEmpty()) throw new RuntimeException("New WeakHashMap is not empty (isEmpty() returned false)");
        // Test that put and get work as expected
        Object key = new Object();
        Object value = new Object();
        map.put(key, value);
        Object retrieved = map.get(key);
        if (retrieved != value) throw new RuntimeException("Value retrieved does not match value put");
    }
}
