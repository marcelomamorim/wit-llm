/*
 * @test
 * @summary Test tailMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P6e5514c3ede8
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConcurrentSkipListMapWitupTest_P6e5514c3ede8 {
    public static void main(String[] args) {
        testTailMapWithExistingKey();
        testTailMapWithNonExistingKey();
        testTailMapWithNullKey();
    }

    static void testTailMapWithExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentNavigableMap<Integer, String> tailMap = map.tailMap(2);
        if (tailMap == null) {
            throw new AssertionError("tailMap returned null for existing key");
        }
        if (tailMap.size() != 2) {
            throw new AssertionError("Expected tailMap size 2, got " + tailMap.size());
        }
        if (!tailMap.firstKey().equals(2)) {
            throw new AssertionError("Expected first key 2, got " + tailMap.firstKey());
        }
        if (!tailMap.lastKey().equals(3)) {
            throw new AssertionError("Expected last key 3, got " + tailMap.lastKey());
        }
        System.out.println("Passed: testTailMapWithExistingKey");
    }

    static void testTailMapWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        ConcurrentNavigableMap<Integer, String> tailMap = map.tailMap(15);
        if (tailMap == null) {
            throw new AssertionError("tailMap returned null for non-existing key");
        }
        if (tailMap.size() != 2) {
            throw new AssertionError("Expected tailMap size 2, got " + tailMap.size());
        }
        if (!tailMap.firstKey().equals(20)) {
            throw new AssertionError("Expected first key 20, got " + tailMap.firstKey());
        }
        if (!tailMap.lastKey().equals(30)) {
            throw new AssertionError("Expected last key 30, got " + tailMap.lastKey());
        }
        System.out.println("Passed: testTailMapWithNonExistingKey");
    }

    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        try {
            map.tailMap(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            System.out.println("Passed: testTailMapWithNullKey");
        }
    }
}
