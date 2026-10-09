/*
 * @test
 * @summary Test tailMap method of ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest_P57d8b90bcb6a
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentNavigableMap;

public class ConcurrentSkipListMapWitupTest_P57d8b90bcb6a {

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
        assert tailMap.size() == 2 : "Expected size 2, got " + tailMap.size();
        assert tailMap.firstKey() == 2 : "Expected first key 2, got " + tailMap.firstKey();
        assert tailMap.lastKey() == 3 : "Expected last key 3, got " + tailMap.lastKey();
        assert tailMap.get(2).equals("two") : "Expected value 'two' for key 2";
        assert tailMap.get(3).equals("three") : "Expected value 'three' for key 3";
    }

    static void testTailMapWithNonExistingKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(30, "thirty");
        ConcurrentNavigableMap<Integer, String> tailMap = map.tailMap(15);
        assert tailMap.size() == 2 : "Expected size 2, got " + tailMap.size();
        assert tailMap.firstKey() == 20 : "Expected first key 20, got " + tailMap.firstKey();
        assert tailMap.get(20).equals("twenty") : "Expected value 'twenty' for key 20";
        assert tailMap.get(30).equals("thirty") : "Expected value 'thirty' for key 30";
    }

    static void testTailMapWithNullKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(5, "five");
        try {
            map.tailMap(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
