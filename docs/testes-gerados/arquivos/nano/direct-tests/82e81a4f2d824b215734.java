/*
 * @test
 * @summary Test firstKey() method of ConcurrentSkipListMap for correct behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testFirstKeyWithElements();
        testFirstKeyEmptyMap();
    }

    static void testFirstKeyWithElements() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(5, "five");
        Integer firstKey = map.firstKey();
        if (firstKey == null || firstKey != 5) {
            throw new AssertionError("Expected firstKey to be 5, but was " + firstKey);
        }
    }

    static void testFirstKeyEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.firstKey();
            throw new AssertionError("Expected NoSuchElementException for empty map");
        } catch (NoSuchElementException e) {
            // expected
        }
    }
}
