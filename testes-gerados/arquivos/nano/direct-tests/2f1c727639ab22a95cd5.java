/*
 * @test    @summary Test floorEntry method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFloorEntryWithExistingKey();
        testFloorEntryWithNonExistingKey();
        testFloorEntryWithNullKey();
    }

    static void testFloorEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Map.Entry<Integer, String> entry = map.floorEntry(20);
        assert entry != null : "Expected non-null entry for key 20";
        assert entry.getKey() == 20 : "Expected key 20";
        assert "twenty".equals(entry.getValue()) : "Expected value 'twenty'";
    }

    static void testFloorEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Map.Entry<Integer, String> entry = map.floorEntry(25);
        assert entry != null : "Expected non-null entry for key 25";
        assert entry.getKey() == 20 : "Expected key 20 as floor for 25";
        assert "twenty".equals(entry.getValue()) : "Expected value 'twenty'";
    }

    static void testFloorEntryWithKeyLessThanAll() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        Map.Entry<Integer, String> entry = map.floorEntry(5);
        assert entry == null : "Expected null for key less than all entries";
    }

    static void testFloorEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");

        try {
            map.floorEntry(null);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
