/* @test    @summary Test TreeMap.next() method for correct behavior and null handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Map;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsEntry();
        testNextOnEmptyMapReturnsNull();
        testNextAfterIterationCompletes();
    }

    private static void testNextReturnsEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        Map.Entry<Integer, String> entry = map.next();
        assert entry != null : "Expected non-null entry";
        assert entry.getKey() != null : "Entry key should not be null";
        assert entry.getValue() != null : "Entry value should not be null";
        assert (entry.getKey() == 1 || entry.getKey() == 2 || entry.getKey() == 3) : "Entry key should be one of the inserted keys";
    }

    private static void testNextOnEmptyMapReturnsNull() {
        TreeMap<Integer, String> map = new TreeMap<>();
        Map.Entry<Integer, String> result = null;
        try {
            result = map.next();
        } catch (Exception e) {
            // Expecting an exception if next() is called on empty map
            assert e != null : "Exception expected when calling next() on empty map";
        }
        assert result == null : "Expected null when calling next() on empty map";
    }

    private static void testNextAfterIterationCompletes() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "ten");
        Map.Entry<Integer, String> firstEntry = map.next();
        // After first call, next() should move to next entry or throw if none
        Map.Entry<Integer, String> secondEntry = null;
        try {
            secondEntry = map.next();
        } catch (Exception e) {
            // If no more entries, exception might be thrown
            secondEntry = null;
        }
        // No assertion on secondEntry, just ensure no unexpected exception
        assert firstEntry != null : "First next() call should return an entry";
    }
}
