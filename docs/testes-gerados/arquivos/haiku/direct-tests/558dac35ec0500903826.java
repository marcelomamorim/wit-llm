/*
 * @test
 * @summary Verify ConcurrentSkipListMap.headMap(K, boolean) bounds and null key handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        testNullKeyThrowsNPE();
        testExclusiveHead();
        testInclusiveHead();
        testEmptyMap();
        System.out.println("PASS");
    }

    static ConcurrentSkipListMap<Integer, String> sample() {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 10; i++) {
            m.put(i, "v" + i);
        }
        return m;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullKeyThrowsNPE() {
        boolean thrown = false;
        try {
            sample().headMap(null, true);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "headMap(null, true) must throw NullPointerException");

        thrown = false;
        try {
            sample().headMap(null, false);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "headMap(null, false) must throw NullPointerException");
    }

    static void testExclusiveHead() {
        ConcurrentNavigableMap<Integer, String> h = sample().headMap(5, false);
        check(h.size() == 4, "exclusive headMap(5) size expected 4 but was " + h.size());
        check(h.containsKey(1), "exclusive headMap(5) must contain 1");
        check(h.containsKey(4), "exclusive headMap(5) must contain 4");
        check(!h.containsKey(5), "exclusive headMap(5) must not contain 5");
        check(h.firstKey() == 1, "exclusive headMap(5) firstKey expected 1");
        check(h.lastKey() == 4, "exclusive headMap(5) lastKey expected 4");
    }

    static void testInclusiveHead() {
        ConcurrentNavigableMap<Integer, String> h = sample().headMap(5, true);
        check(h.size() == 5, "inclusive headMap(5) size expected 5 but was " + h.size());
        check(h.containsKey(5), "inclusive headMap(5) must contain 5");
        check(!h.containsKey(6), "inclusive headMap(5) must not contain 6");
        check(h.lastKey() == 5, "inclusive headMap(5) lastKey expected 5");
    }

    static void testEmptyMap() {
        ConcurrentNavigableMap<Integer, String> h =
            new ConcurrentSkipListMap<Integer, String>().headMap(3, true);
        check(h.isEmpty(), "headMap on empty map must be empty");
        check(h.size() == 0, "headMap on empty map size must be 0");
    }
}
