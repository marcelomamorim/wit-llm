/*
 * @test
 * @summary Test ConcurrentSkipListMap.SubMap.SubMapValueIterator.next() for NoSuchElementException and normal behavior
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListMap.SubMap;
import java.util.concurrent.ConcurrentSkipListMap.SubMap.SubMapValueIterator;

public class ConcurrentSkipListMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsValueAndAdvances();
        testNextThrowsNoSuchElementException();
    }

    private static void testNextReturnsValueAndAdvances() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");

        // Create a SubMap view from 1 (inclusive) to 3 (exclusive)
        SubMap<Integer, String> subMap = new SubMap<>(map, 1, true, 3, false);

        // Create a SubMapValueIterator from the SubMap
        SubMapValueIterator it = subMap.new SubMapValueIterator();

        // The first call to next() should return "one"
        String first = it.next();
        if (!"one".equals(first)) {
            throw new AssertionError("Expected 'one' but got: " + first);
        }

        // The second call to next() should return "two"
        String second = it.next();
        if (!"two".equals(second)) {
            throw new AssertionError("Expected 'two' but got: " + second);
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        // Create a SubMap view with no elements
        SubMap<Integer, String> subMap = new SubMap<>(map, 1, true, 2, false);

        // Create a SubMapValueIterator from the SubMap
        SubMapValueIterator it = subMap.new SubMapValueIterator();

        // The first call to next() should throw NoSuchElementException because nextValue is null
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        }
    }
}
