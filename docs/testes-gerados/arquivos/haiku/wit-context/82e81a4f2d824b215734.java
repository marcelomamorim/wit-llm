/*
 * @test
 * @summary Checks firstKey on ConcurrentSkipListMap sub-map views, including empty ranges and descending views
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    interface Action {
        void run();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNoSuchElement(Action action, String message) {
        try {
            action.run();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testEmptyMapFirstKeyThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        expectNoSuchElement(() -> map.firstKey(), "firstKey on empty map must throw NoSuchElementException");
    }

    static void testEmptySubMapFirstKeyThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        expectNoSuchElement(() -> map.subMap(1, true, 5, true).firstKey(),
                "firstKey on empty ascending sub-map must throw NoSuchElementException");
    }

    static void testEmptyDescendingSubMapFirstKeyThrows() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(20, "b");
        expectNoSuchElement(() -> map.descendingMap().subMap(5, true, 1, true).firstKey(),
                "firstKey on empty descending sub-map must throw NoSuchElementException");
    }

    static void testAscendingSubMapFirstKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(10, "b");
        map.put(20, "c");
        Integer first = map.subMap(5, true, 15, true).firstKey();
        check(first.intValue() == 10, "expected 10 but was " + first);
    }

    static void testExclusiveBoundsSubMapFirstKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(10, "a");
        map.put(15, "b");
        map.put(20, "c");
        Integer first = map.subMap(10, false, 20, false).firstKey();
        check(first.intValue() == 15, "expected 15 but was " + first);
    }

    static void testDescendingSubMapFirstKey() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(10, "b");
        map.put(20, "c");
        Integer first = map.descendingMap().subMap(15, true, 5, true).firstKey();
        check(first.intValue() == 10, "expected 10 in descending view but was " + first);
    }

    public static void main(String[] args) {
        testEmptyMapFirstKeyThrows();
        testEmptySubMapFirstKeyThrows();
        testEmptyDescendingSubMapFirstKeyThrows();
        testAscendingSubMapFirstKey();
        testExclusiveBoundsSubMapFirstKey();
        testDescendingSubMapFirstKey();
    }
}
