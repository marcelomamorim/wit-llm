/*
 * @test
 * @summary Test ConcurrentSkipListMap.firstKey() behavior including exception paths
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testFirstKeyOnEmptyMap();
        testFirstKeyOnNonEmptyMap();
    }

    private static void testFirstKeyOnEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.firstKey();
            throw new RuntimeException("Expected NoSuchElementException for firstKey() on empty map");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }

    private static void testFirstKeyOnNonEmptyMap() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "ten");
        map.put(20, "twenty");
        map.put(5, "five");

        Integer firstKey = map.firstKey();
        if (!firstKey.equals(5)) {
            throw new RuntimeException("firstKey() returned " + firstKey + ", expected 5");
        }
    }
}
