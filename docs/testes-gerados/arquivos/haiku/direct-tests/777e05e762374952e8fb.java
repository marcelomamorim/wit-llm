/*
 * @test
 * @summary Verifies containsKey on ConcurrentSkipListMap range views respects bounds and rejects null keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void expectNpe(ConcurrentNavigableMap<Integer, Integer> map, String message) {
        try {
            map.containsKey(null);
            failures++;
            System.err.println("FAILED (no NPE): " + message);
        } catch (NullPointerException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            map.put(i, i * 10);
        }

        ConcurrentNavigableMap<Integer, Integer> sub = map.subMap(2, true, 4, false);
        check(sub.containsKey(2), "lower bound inclusive: 2 should be contained");
        check(sub.containsKey(3), "3 should be contained");
        check(!sub.containsKey(4), "upper bound exclusive: 4 should not be contained");
        check(!sub.containsKey(1), "1 below range should not be contained");
        check(!sub.containsKey(5), "5 above range should not be contained");

        map.remove(3);
        check(!sub.containsKey(3), "removed key 3 should not be contained in view");

        ConcurrentNavigableMap<Integer, Integer> head = map.headMap(3);
        check(head.containsKey(1), "1 should be in headMap(3)");
        check(head.containsKey(2), "2 should be in headMap(3)");
        check(!head.containsKey(3), "3 should not be in headMap(3)");
        check(!head.containsKey(4), "4 should not be in headMap(3)");

        ConcurrentNavigableMap<Integer, Integer> tail = map.tailMap(4, true);
        check(tail.containsKey(4), "4 should be in tailMap(4, true)");
        check(tail.containsKey(5), "5 should be in tailMap(4, true)");
        check(!tail.containsKey(3), "3 should not be in tailMap(4, true)");

        expectNpe(sub, "null key on subMap view");
        expectNpe(head, "null key on headMap view");
        expectNpe(tail, "null key on tailMap view");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
