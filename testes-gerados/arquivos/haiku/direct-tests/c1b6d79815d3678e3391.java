/*
 * @test
 * @summary Verifies EnumMap collection-view iterator remove() semantics: removal of the last returned element, IllegalStateException when remove() is called without a preceding next() or twice in a row.
 * @run main EnumMapWitupTest
 */

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

public class EnumMapWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testKeySetIteratorRemove();
        testValuesIteratorRemove();
        testEntrySetIteratorRemoveAll();
        testRemoveBeforeNextThrows();
        testRemoveTwiceThrows();
    }

    static EnumMap<Color, String> sample() {
        EnumMap<Color, String> m = new EnumMap<>(Color.class);
        m.put(Color.RED, "r");
        m.put(Color.GREEN, "g");
        m.put(Color.BLUE, "b");
        return m;
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }

    static void testKeySetIteratorRemove() {
        EnumMap<Color, String> m = sample();
        Iterator<Color> it = m.keySet().iterator();
        check(it.next() == Color.RED, "first key should be RED");
        it.remove();
        check(!m.containsKey(Color.RED), "RED should be removed from map");
        check(m.size() == 2, "size should be 2 after remove, was " + m.size());
        check(it.next() == Color.GREEN, "next key after removal should be GREEN");
    }

    static void testValuesIteratorRemove() {
        EnumMap<Color, String> m = sample();
        Iterator<String> it = m.values().iterator();
        check("r".equals(it.next()), "first value should be r");
        it.remove();
        check(m.size() == 2, "size should be 2 after values remove");
        check(!m.containsValue("r"), "value r should be gone");
    }

    static void testEntrySetIteratorRemoveAll() {
        EnumMap<Color, String> m = sample();
        Iterator<Map.Entry<Color, String>> it = m.entrySet().iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        check(m.isEmpty(), "map should be empty after removing all entries");
        check(m.size() == 0, "size should be 0");
    }

    static void testRemoveBeforeNextThrows() {
        EnumMap<Color, String> m = sample();
        Iterator<Color> it = m.keySet().iterator();
        try {
            it.remove();
            throw new RuntimeException("remove() before next() should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(m.size() == 3, "map should be unchanged");
    }

    static void testRemoveTwiceThrows() {
        EnumMap<Color, String> m = sample();
        Iterator<Color> it = m.keySet().iterator();
        it.next();
        it.remove();
        try {
            it.remove();
            throw new RuntimeException("second remove() without next() should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(m.size() == 2, "size should remain 2 after failed second remove");
    }
}
