/*
 * @test
 * @summary Test ConcurrentSkipListMap.putIfAbsent behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test putIfAbsent inserts new key-value pair
        String prev = map.putIfAbsent("key1", "value1");
        if (prev != null) throw new RuntimeException("Expected null on first putIfAbsent");
        if (!"value1".equals(map.get("key1"))) throw new RuntimeException("Value not inserted correctly");

        // Test putIfAbsent does not overwrite existing value
        prev = map.putIfAbsent("key1", "value2");
        if (!"value1".equals(prev)) throw new RuntimeException("Expected previous value on putIfAbsent");
        if (!"value1".equals(map.get("key1"))) throw new RuntimeException("Value should not be overwritten by putIfAbsent");

        // Test putIfAbsent with null value allowed (ConcurrentSkipListMap allows null values? No, it does not allow null keys or values, so expect NullPointerException)
        boolean npeThrown = false;
        try {
            map.putIfAbsent("key2", null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) throw new RuntimeException("Expected NullPointerException when inserting null value");

        // Test putIfAbsent with null key throws NullPointerException
        npeThrown = false;
        try {
            map.putIfAbsent(null, "value");
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) throw new RuntimeException("Expected NullPointerException when inserting null key");
    }
}
