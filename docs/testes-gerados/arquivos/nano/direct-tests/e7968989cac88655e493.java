/*
 * @test    @summary Test ceilingEntry method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testCeilingEntryWithExistingKey();
        testCeilingEntryWithNonExistingKey();
        testCeilingEntryWithNullKey();
    }

    static void testCeilingEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Map.Entry<Integer, String> entry = map.ceilingEntry(20);
        assert entry != null : "Expected non-null entry for existing key";
        assert entry.getKey() == 20 : "Expected key 20";
        assert "twenty".equals(entry.getValue()) : "Expected value 'twenty'";
    }

    static void testCeilingEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        Map.Entry<Integer, String> entry = map.ceilingEntry(25);
        assert entry != null : "Expected non-null entry for key 25";
        assert entry.getKey() == 30 : "Expected key 30";
        assert "thirty".equals(entry.getValue()) : "Expected value 'thirty'";
    }

    static void testCeilingEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        try {
            map.ceilingEntry(null);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
