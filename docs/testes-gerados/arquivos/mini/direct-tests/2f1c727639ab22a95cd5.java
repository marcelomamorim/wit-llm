/*
 * @test
 * @summary Test ConcurrentSkipListMap.floorEntry(K) behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // floorEntry on empty map returns null
        if (map.floorEntry(10) != null) {
            throw new AssertionError("Expected null for floorEntry on empty map");
        }

        // Insert some entries
        map.put(5, "five");
        map.put(10, "ten");
        map.put(20, "twenty");

        // floorEntry with exact key present
        Map.Entry<Integer, String> e = map.floorEntry(10);
        if (e == null || !e.getKey().equals(10) || !"ten".equals(e.getValue())) {
            throw new AssertionError("floorEntry(10) should return entry with key 10 and value 'ten'");
        }

        // floorEntry with key between existing keys
        e = map.floorEntry(15);
        if (e == null || !e.getKey().equals(10) || !"ten".equals(e.getValue())) {
            throw new AssertionError("floorEntry(15) should return entry with key 10 and value 'ten'");
        }

        // floorEntry with key less than all keys
        e = map.floorEntry(1);
        if (e != null) {
            throw new AssertionError("floorEntry(1) should return null as no floor key exists");
        }

        // floorEntry with key greater than all keys
        e = map.floorEntry(100);
        if (e == null || !e.getKey().equals(20) || !"twenty".equals(e.getValue())) {
            throw new AssertionError("floorEntry(100) should return entry with key 20 and value 'twenty'");
        }

        System.out.println("All tests passed.");
    }
}
