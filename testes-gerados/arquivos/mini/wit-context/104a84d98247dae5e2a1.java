/*
 * @test
 * @summary Test ConcurrentSkipListMap.lastKey() behavior including exception paths
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testLastKeyNormal();
        testLastKeyEmptyMapThrows();
    }

    private static void testLastKeyNormal() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(3, "three");
        map.put(2, "two");

        Integer lastKey = map.lastKey();
        if (lastKey == null || lastKey != 3) {
            throw new AssertionError("Expected lastKey to be 3, got: " + lastKey);
        }
    }

    private static void testLastKeyEmptyMapThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        try {
            map.lastKey();
            throw new AssertionError("Expected NoSuchElementException for lastKey on empty map");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
