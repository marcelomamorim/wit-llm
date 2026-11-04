/*
 * @test
 * @summary Witup-derived tests for ConcurrentSkipListMap.lowerKey
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(empty.lowerKey(1) == null, "lowerKey on empty map must return null");

        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        m.put(1, "a");
        m.put(3, "c");
        m.put(5, "e");
        check(m.lowerKey(3).equals(1), "lowerKey(3) must be 1");
        check(m.lowerKey(4).equals(3), "lowerKey(4) must be 3");
        check(m.lowerKey(10).equals(5), "lowerKey(10) must be 5");
        check(m.lowerKey(1) == null, "lowerKey(1) must be null");
        check(m.lowerKey(0) == null, "lowerKey(0) must be null");

        check(throwsNpe(empty), "lowerKey(null) on empty map must throw NullPointerException");
        check(throwsNpe(m), "lowerKey(null) on non-empty map must throw NullPointerException");

        ConcurrentSkipListMap<Integer, String> rev =
                new ConcurrentSkipListMap<>(Collections.reverseOrder());
        rev.put(1, "a");
        rev.put(3, "c");
        rev.put(5, "e");
        check(rev.lowerKey(3).equals(5), "reverse-order lowerKey(3) must be 5");
        check(rev.lowerKey(5) == null, "reverse-order lowerKey(5) must be null");
    }

    private static boolean throwsNpe(ConcurrentSkipListMap<Integer, String> map) {
        try {
            map.lowerKey(null);
            return false;
        } catch (NullPointerException e) {
            return true;
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
