/*
 * @test
 * @summary Witup-generated checks for ConcurrentSkipListMap values view removeIf
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testRemovesMatchingValues();
        testNoMatchReturnsFalse();
        testSubMapValuesRemoveIf();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testNullFilterThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        boolean thrown = false;
        try {
            m.values().removeIf(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "removeIf(null) must throw NullPointerException");
        check(m.size() == 1, "map must be unchanged after NPE");
    }

    static void testRemovesMatchingValues() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "bb");
        m.put(3, "a");
        m.put(4, "ccc");
        boolean removed = m.values().removeIf(new Predicate<String>() {
            @Override
            public boolean test(String v) {
                return v.equals("a");
            }
        });
        check(removed, "removeIf must return true when values were removed");
        check(m.size() == 2, "two matching entries must be removed");
        check(!m.containsKey(1), "key 1 must be removed");
        check(!m.containsKey(3), "key 3 must be removed");
        check(m.containsKey(2), "key 2 must remain");
        check(m.containsKey(4), "key 4 must remain");
    }

    static void testNoMatchReturnsFalse() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(2, "b");
        boolean removed = m.values().removeIf(new Predicate<String>() {
            @Override
            public boolean test(String v) {
                return v.isEmpty();
            }
        });
        check(!removed, "removeIf must return false when nothing matches");
        check(m.size() == 2, "map size must be unchanged when nothing matches");
    }

    static void testSubMapValuesRemoveIf() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "x" + i);
        }
        ConcurrentSkipListMap<Integer, String> head = (ConcurrentSkipListMap<Integer, String>) m.headMap(3);
        boolean removed = head.values().removeIf(new Predicate<String>() {
            @Override
            public boolean test(String v) {
                return v.equals("x1");
            }
        });
        check(removed, "submap removeIf must return true when a value matches");
        check(!m.containsKey(1), "key 1 must be removed from backing map");
        check(m.containsKey(2), "key 2 must remain");
        check(m.containsKey(3), "key 3 is outside the head view and must remain");
    }
}
