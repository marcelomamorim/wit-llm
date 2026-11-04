/*
 * @test
 * @summary Verifies subMap(fromKey, toKey) null-key NullPointerException, range view semantics and inconsistent-range IllegalArgumentException on ConcurrentSkipListMap
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Runnable r, String message) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void expectIae(Runnable r, String message) {
        try {
            r.run();
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        map.put(7, "g");

        ConcurrentNavigableMap<Integer, String> sub = map.subMap(1, 5);
        check(sub.size() == 2, "subMap(1,5) size expected 2");
        check(sub.containsKey(1), "subMap(1,5) should contain 1");
        check(sub.containsKey(3), "subMap(1,5) should contain 3");
        check(!sub.containsKey(5), "subMap(1,5) must exclude toKey 5");
        check(!sub.containsKey(7), "subMap(1,5) must not contain 7");
        check(sub.firstKey().equals(1), "subMap(1,5) firstKey expected 1");
        check(sub.lastKey().equals(3), "subMap(1,5) lastKey expected 3");

        ConcurrentNavigableMap<Integer, String> same = map.subMap(3, 3);
        check(same.isEmpty(), "subMap(3,3) should be empty");

        expectNpe(() -> map.subMap(null, 5), "subMap(null, 5) expected NullPointerException");
        expectNpe(() -> map.subMap(1, null), "subMap(1, null) expected NullPointerException");
        expectNpe(() -> map.subMap(null, null), "subMap(null, null) expected NullPointerException");

        expectIae(() -> map.subMap(5, 1), "subMap(5, 1) expected IllegalArgumentException");
    }
}
