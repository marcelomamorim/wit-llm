/*
 * @test
 * @summary Tests ConcurrentSkipListMap submap get bounds checking and null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");

        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, true, 5, false);
        check("a".equals(sub.get(1)), "subMap lower inclusive bound should return value");
        check("c".equals(sub.get(3)), "subMap interior key should return value");
        check(sub.get(5) == null, "subMap exclusive upper bound should return null");
        check(sub.get(0) == null, "subMap key below lower bound should return null");
        check(sub.get(6) == null, "subMap key above upper bound should return null");

        ConcurrentNavigableMap<Integer, String> head = map.headMap(3);
        check(head.get(3) == null, "headMap exclusive bound should return null");
        check("a".equals(head.get(1)), "headMap should return value below bound");

        ConcurrentNavigableMap<Integer, String> tail = map.tailMap(3);
        check("c".equals(tail.get(3)), "tailMap inclusive bound should return value");
        check(tail.get(1) == null, "tailMap key below bound should return null");

        boolean npe = false;
        try {
            sub.get(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "submap get(null) should throw NullPointerException");

        npe = false;
        try {
            map.get(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "map get(null) should throw NullPointerException");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
