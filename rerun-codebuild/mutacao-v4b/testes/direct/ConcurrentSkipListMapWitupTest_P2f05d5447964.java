/* @test
   @summary Verifies tailMap method behavior of ConcurrentSkipListMap.
   @run main ConcurrentSkipListMapWitupTest_P2f05d5447964
 */
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest_P2f05d5447964 {
    public static void main(String[] args) {
        testTailMapWithExistingKey();
        testTailMapWithNonExistingKey();
        testTailMapWithNullKey();
    }

    static void testTailMapWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");
        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(3);
        if (tail == null) {
            throw new AssertionError("tailMap returned null for existing key");
        }
        if (tail.size() != 2) {
            throw new AssertionError("Expected size 2, got " + tail.size());
        }
        if (!tail.firstKey().equals(3)) {
            throw new AssertionError("First key of tailMap should be 3");
        }
        if (!tail.lastKey().equals(5)) {
            throw new AssertionError("Last key of tailMap should be 5");
        }
        if (!tail.get(3).equals("three")) {
            throw new AssertionError("Value for key 3 should be 'three'");
        }
    }

    static void testTailMapWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(2, "two");
        map.put(4, "four");
        map.put(6, "six");
        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(5);
        if (tail == null) {
            throw new AssertionError("tailMap returned null for non-existing key");
        }
        if (tail.size() != 2) {
            throw new AssertionError("Expected size 2, got " + tail.size());
        }
        if (!tail.firstKey().equals(6)) {
            throw new AssertionError("First key of tailMap should be 6");
        }
        if (!tail.lastKey().equals(6)) {
            throw new AssertionError("Last key of tailMap should be 6");
        }
        if (!tail.get(6).equals("six")) {
            throw new AssertionError("Value for key 6 should be 'six'");
        }
    }

    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        try {
            map.tailMap(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
