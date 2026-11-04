/*
 * @test
 * @summary Test ConcurrentSkipListMap.lowerEntry(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");

        // Test normal behavior: lowerEntry returns entry with key less than given key
        Map.Entry<String, String> e1 = map.lowerEntry("b");
        if (e1 == null || !"a".equals(e1.getKey()) || !"alpha".equals(e1.getValue())) {
            throw new AssertionError("lowerEntry did not return expected entry for key 'b'");
        }

        // Test lowerEntry with key less than any map key returns null
        Map.Entry<String, String> e2 = map.lowerEntry("a");
        if (e2 != null) {
            throw new AssertionError("lowerEntry should return null for key 'a' as no lower key exists");
        }

        // Test lowerEntry with key greater than all keys returns last entry
        Map.Entry<String, String> e3 = map.lowerEntry("z");
        if (e3 == null || !"c".equals(e3.getKey()) || !"gamma".equals(e3.getValue())) {
            throw new AssertionError("lowerEntry did not return expected entry for key 'z'");
        }

        // Test lowerEntry with null key triggers NullPointerException
        boolean npeThrown = false;
        try {
            map.lowerEntry(null);
        } catch (NullPointerException ex) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("lowerEntry did not throw NullPointerException for null key");
        }
    }
}
