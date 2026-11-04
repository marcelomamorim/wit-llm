/*
 * @test
 * @summary Test ConcurrentSkipListMap.floorEntry(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, Integer> map = new ConcurrentSkipListMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        // Test floorEntry with existing key
        Map.Entry<String, Integer> e = map.floorEntry("b");
        if (e == null || !"b".equals(e.getKey()) || e.getValue() != 2) {
            throw new AssertionError("floorEntry failed for existing key 'b'");
        }

        // Test floorEntry with key between existing keys
        e = map.floorEntry("bb");
        if (e == null || !"b".equals(e.getKey())) {
            throw new AssertionError("floorEntry failed for key 'bb' between keys");
        }

        // Test floorEntry with key less than all keys
        e = map.floorEntry("0");
        if (e != null) {
            throw new AssertionError("floorEntry should return null for key less than all keys");
        }

        // Test floorEntry with null key - expect NullPointerException
        try {
            map.floorEntry(null);
            throw new AssertionError("floorEntry(null) did not throw NullPointerException");
        } catch (NullPointerException ex) {
            // expected
        }
    }
}
