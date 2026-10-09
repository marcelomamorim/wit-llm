/*
 * @test    @summary Test tailMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_Pf593288d8120
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConcurrentSkipListMapWitupTest_Pf593288d8120 {
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
        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(2);
        assert tail.size() == 2 : "Expected size 2, got " + tail.size();
        assert tail.firstKey() == 2 : "Expected first key 2, got " + tail.firstKey();
        assert tail.lastKey() == 3 : "Expected last key 3, got " + tail.lastKey();
        assert tail.get(2).equals("two") : "Expected value 'two', got " + tail.get(2);
        assert tail.get(3).equals("three") : "Expected value 'three', got " + tail.get(3);
    }
    
    static void testTailMapWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(15);
        assert tail.size() == 1 : "Expected size 1, got " + tail.size();
        assert tail.firstKey() == 20 : "Expected first key 20, got " + tail.firstKey();
        assert tail.get(20).equals("twenty") : "Expected value 'twenty', got " + tail.get(20);
    }
    
    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        boolean exceptionThrown = false;
        try {
            map.tailMap(null); // Should throw NullPointerException
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null key";
    }
}
