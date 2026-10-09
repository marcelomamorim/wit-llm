/*
 * @test
 * @summary Test ConcurrentSkipListMap.tailMap(K) for null argument and normal usage
 * @run main ConcurrentSkipListMapWitupTest_P439c946d0cb7
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConcurrentSkipListMapWitupTest_P439c946d0cb7 {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        // Test normal tailMap usage with non-null key
        ConcurrentNavigableMap<String, String> tail = map.tailMap("b");
        if (!tail.containsKey("b") || !tail.containsKey("c") || tail.containsKey("a")) {
            throw new AssertionError("tailMap did not return expected submap");
        }

        // Test tailMap with null key triggers NullPointerException
        try {
            map.tailMap(null);
            throw new AssertionError("Expected NullPointerException for null fromKey");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
