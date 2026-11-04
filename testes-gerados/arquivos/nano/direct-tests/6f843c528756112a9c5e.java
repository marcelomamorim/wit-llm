/* @test    @summary Test tailMap method of TreeMap for valid and invalid inputs    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.NavigableMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTailMapValid();
        testTailMapInvalid();
    }

    static void testTailMapValid() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");
        // fromKey within range
        NavigableMap<Integer, String> tail = map.tailMap(2, true);
        assert tail.size() == 2 : "Expected size 2, got " + tail.size();
        assert tail.firstKey() == 3 : "Expected first key 3, got " + tail.firstKey();
        assert tail.lastKey() == 5 : "Expected last key 5, got " + tail.lastKey();
    }

    static void testTailMapInvalid() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(5, "five");
        boolean exceptionThrown = false;
        try {
            // fromKey out of range (less than smallest key)
            map.tailMap(-1, true);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for out of range fromKey";
    }
}
