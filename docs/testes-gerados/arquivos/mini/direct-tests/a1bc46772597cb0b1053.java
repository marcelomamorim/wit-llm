/*
 * @test
 * @summary Test TreeMap.next() method behavior
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        testNextReturnsLastKey();
        testNextThrowsWhenNoPreviousEntry();
    }

    static void testNextReturnsLastKey() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        Iterator<Integer> descendingKeyIterator = map.descendingKeySet().iterator();

        // The first call to next() should return the highest key (3)
        Integer key = descendingKeyIterator.next();
        if (!key.equals(3)) {
            throw new AssertionError("Expected 3 but got " + key);
        }

        // The second call to next() should return the next lower key (2)
        key = descendingKeyIterator.next();
        if (!key.equals(2)) {
            throw new AssertionError("Expected 2 but got " + key);
        }

        // The third call to next() should return the next lower key (1)
        key = descendingKeyIterator.next();
        if (!key.equals(1)) {
            throw new AssertionError("Expected 1 but got " + key);
        }
    }

    static void testNextThrowsWhenNoPreviousEntry() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // Empty map descending iterator
        Iterator<Integer> descendingKeyIterator = map.descendingKeySet().iterator();

        try {
            descendingKeyIterator.next();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException expected) {
            // Expected exception
        }
    }
}
