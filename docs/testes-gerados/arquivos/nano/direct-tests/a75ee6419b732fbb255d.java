/*
 * @test
 * @summary Test lowerEntry method of ConcurrentSkipListMap for various keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testLowerEntryWithExistingKey();
        testLowerEntryWithNonExistingKey();
        testLowerEntryWithNullKey();
    }

    static void testLowerEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "A");
        map.put(20, "B");
        map.put(30, "C");
        Map.Entry<Integer, String> entry = map.lowerEntry(25);
        if (entry == null || entry.getKey() != 20 || !"B".equals(entry.getValue())) {
            throw new AssertionError("Expected lowerEntry for key 25 to be (20, B), but got: " + entry);
        }
    }

    static void testLowerEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "X");
        map.put(15, "Y");
        map.put(25, "Z");
        Map.Entry<Integer, String> entry = map.lowerEntry(5);
        if (entry != null) {
            throw new AssertionError("Expected lowerEntry for key 5 to be null, but got: " + entry);
        }
    }

    static void testLowerEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.lowerEntry(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
