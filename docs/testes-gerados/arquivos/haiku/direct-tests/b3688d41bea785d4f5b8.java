/*
 * @test
 * @summary Verifies remove() on the descending key iterator of TreeMap, which delegates to removeDescending()
 * @run main TreeMapWitupTest
 */

import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveAfterNextRemovesLastReturned();
        testRemoveWithoutNextThrowsIllegalState();
        testRemoveTwiceThrowsIllegalState();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static TreeMap<Integer, String> newMap() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        return m;
    }

    static void testRemoveAfterNextRemovesLastReturned() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(it.hasNext(), "descending iterator should have elements");
        int first = it.next();
        check(first == 3, "first descending key should be 3, was " + first);
        it.remove();
        check(!m.containsKey(3), "key 3 should be removed from map");
        check(m.size() == 2, "map size should be 2, was " + m.size());
        check(m.lastKey() == 2, "last key should now be 2");
        check(it.hasNext(), "iterator should still have remaining elements");
        int second = it.next();
        check(second == 2, "next descending key should be 2, was " + second);
    }

    static void testRemoveWithoutNextThrowsIllegalState() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() should throw IllegalStateException");
        check(m.size() == 3, "map should be unchanged, size was " + m.size());
    }

    static void testRemoveTwiceThrowsIllegalState() {
        TreeMap<Integer, String> m = newMap();
        Iterator<Integer> it = m.descendingKeySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second consecutive remove() should throw IllegalStateException");
        check(m.size() == 2, "map size should remain 2, was " + m.size());
    }
}
