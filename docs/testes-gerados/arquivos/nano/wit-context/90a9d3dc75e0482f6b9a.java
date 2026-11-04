/*
 * @test
 * @summary Test for ConcurrentSkipListMap.next() method, focusing on normal and exceptional behavior.
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NoSuchElementException;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNextReturnsValueAndAdvances();
        testNextThrowsWhenNoNext();
    }

    static void testNextReturnsValueAndAdvances() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        ConcurrentSkipListMap.SubMap.SubMapValueIterator<Integer, String> iterator =
            (ConcurrentSkipListMap.SubMap.SubMapValueIterator<Integer, String>) map.new SubMap.SubMapValueIterator();
        // Initialize iterator to start
        iterator.advance();
        String value = iterator.next();
        assert value != null : "Expected non-null value";
        // After next, iterator should have advanced
        String nextValue = iterator.next();
        assert nextValue != null : "Expected non-null value after second next";
    }

    static void testNextThrowsWhenNoNext() {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "one");
        ConcurrentSkipListMap.SubMap.SubMapValueIterator<Integer, String> iterator =
            (ConcurrentSkipListMap.SubMap.SubMapValueIterator<Integer, String>) map.new SubMap.SubMapValueIterator();
        // Initialize iterator
        iterator.advance();
        // Consume the only element
        iterator.next();
        boolean thrown = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        assert thrown : "Expected NoSuchElementException when calling next() with no elements";
    }
}
