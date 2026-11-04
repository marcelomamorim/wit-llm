/*
 * @test
 * @summary Test ConcurrentSkipListMap.higherEntry(K) for null key and normal keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();

        // Test higherEntry with null key - expect NullPointerException
        try {
            map.higherEntry(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test higherEntry with empty map - expect null result
        Map.Entry<String, String> result = map.higherEntry("a");
        if (result != null) {
            throw new RuntimeException("Expected null for higherEntry on empty map");
        }

        // Add some entries
        map.put("a", "1");
        map.put("c", "3");
        map.put("e", "5");

        // Test higherEntry with key less than first key
        result = map.higherEntry("0");
        if (result == null || !"a".equals(result.getKey()) || !"1".equals(result.getValue())) {
            throw new RuntimeException("Expected higherEntry of '0' to be entry with key 'a'");
        }

        // Test higherEntry with key equal to existing key
        result = map.higherEntry("a");
        if (result == null || !"c".equals(result.getKey()) || !"3".equals(result.getValue())) {
            throw new RuntimeException("Expected higherEntry of 'a' to be entry with key 'c'");
        }

        // Test higherEntry with key between existing keys
        result = map.higherEntry("b");
        if (result == null || !"c".equals(result.getKey()) || !"3".equals(result.getValue())) {
            throw new RuntimeException("Expected higherEntry of 'b' to be entry with key 'c'");
        }

        // Test higherEntry with key equal to last key
        result = map.higherEntry("e");
        if (result != null) {
            throw new RuntimeException("Expected null for higherEntry of last key 'e'");
        }

        // Test higherEntry with key greater than last key
        result = map.higherEntry("z");
        if (result != null) {
            throw new RuntimeException("Expected null for higherEntry of key 'z' greater than last key");
        }
    }
}
