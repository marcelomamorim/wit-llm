/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap.tailMap(K)
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullFromKeyThrowsNPE();
        testTailMapIsInclusive();
        testTailMapBetweenKeys();
        testTailMapAboveAllKeysIsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static ConcurrentSkipListMap<Integer, String> sample() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(5, "b");
        m.put(10, "c");
        m.put(20, "d");
        return m;
    }

    static void testNullFromKeyThrowsNPE() {
        ConcurrentSkipListMap<Integer, String> m = sample();
        boolean thrown = false;
        try {
            m.tailMap(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "tailMap(null) should throw NullPointerException");
    }

    static void testTailMapIsInclusive() {
        ConcurrentNavigableMap<Integer, String> t = sample().tailMap(10);
        check(t.size() == 2, "tailMap(10) should contain keys 10 and 20");
        check(t.firstKey().intValue() == 10, "tailMap(10) first key should be 10");
        check(t.containsKey(Integer.valueOf(10)), "tailMap(10) should include the bound key");
        check(!t.containsKey(Integer.valueOf(5)), "tailMap(10) should exclude keys below the bound");
    }

    static void testTailMapBetweenKeys() {
        ConcurrentNavigableMap<Integer, String> t = sample().tailMap(7);
        check(t.size() == 2, "tailMap(7) should contain keys 10 and 20");
        check(t.firstKey().intValue() == 10, "tailMap(7) first key should be 10");
    }

    static void testTailMapAboveAllKeysIsEmpty() {
        ConcurrentNavigableMap<Integer, String> t = sample().tailMap(25);
        check(t.isEmpty(), "tailMap(25) should be empty when no key is >= 25");
    }
}
