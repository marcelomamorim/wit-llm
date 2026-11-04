/*
 * @test
 * @summary Verify TreeMap descending key iterator remove() behavior through the public descendingKeySet() API
 * @run main TreeMapWitupTest
 */

import java.util.Iterator;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testRemoveAfterNextRemovesLastReturnedKey();
        testRemoveWithoutNextThrowsIllegalStateException();
        testSecondRemoveWithoutNextThrowsIllegalStateException();
        testRemoveAfterStructuralModificationThrowsConcurrentModificationException();
    }

    static void testRemoveAfterNextRemovesLastReturnedKey() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        check(it.next() == 3, "descending iterator should return highest key first");
        it.remove();
        check(!m.containsKey(3), "remove() should delete the last returned key");
        check(m.size() == 2, "size should decrease by one after remove()");
        check(m.firstKey() == 1, "lowest key should remain");
        check(m.lastKey() == 2, "new highest key should be 2");
    }

    static void testRemoveWithoutNextThrowsIllegalStateException() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "remove() before next() should throw IllegalStateException");
        check(m.size() == 2, "map must not change when remove() fails");
    }

    static void testSecondRemoveWithoutNextThrowsIllegalStateException() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        it.next();
        it.remove();
        boolean thrown = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "second remove() without intervening next() should throw IllegalStateException");
        check(m.size() == 1, "only the first returned key should have been removed");
    }

    static void testRemoveAfterStructuralModificationThrowsConcurrentModificationException() {
        TreeMap<Integer, String> m = new TreeMap<>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Iterator<Integer> it = m.descendingKeySet().iterator();
        it.next();
        m.put(4, "d");
        boolean thrown = false;
        try {
            it.remove();
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "remove() after external structural modification should throw ConcurrentModificationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
