/*
 * @test
 * @summary Test higherEntry method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.Map;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testHigherEntryWithExistingKey();
        testHigherEntryWithNonExistingKey();
        testHigherEntryWithNullKey();
    }

    static void testHigherEntryWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");

        Map.Entry<Integer, String> result = map.higherEntry(20);
        assert result != null : "Expected non-null result for key 20";
        assert result.getKey() == 30 : "Expected key 30, got " + result.getKey();
        assert result.getValue().equals("Thirty") : "Expected value 'Thirty', got " + result.getValue();
    }

    static void testHigherEntryWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");

        Map.Entry<Integer, String> result = map.higherEntry(25);
        assert result != null : "Expected non-null result for key 25";
        assert result.getKey() == 30 : "Expected key 30, got " + result.getKey();
        assert result.getValue().equals("Thirty") : "Expected value 'Thirty', got " + result.getValue();
    }

    static void testHigherEntryWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "Ten");
        map.put(20, "Twenty");
        map.put(30, "Thirty");

        try {
            map.higherEntry(null);
            assert false : "Expected NullPointerException for null key";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
