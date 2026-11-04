/*
 * @test
 * @summary Witup-derived checks for ConcurrentSkipListMap submap get with null and out-of-bounds keys
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "v" + i);
        }
        ConcurrentNavigableMap<Integer, String> sub = map.subMap(3, true, 7, false);

        checkNullKeyThrowsNpe(sub);
        checkEquals("v3", sub.get(3));
        checkEquals("v6", sub.get(6));
        checkEquals(null, sub.get(7));
        checkEquals(null, sub.get(2));
        checkEquals(null, sub.get(10));
    }

    private static void checkNullKeyThrowsNpe(ConcurrentNavigableMap<Integer, String> sub) {
        try {
            sub.get(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }

    private static void checkEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
