/*
 * @test
 * @summary Verifies that clear() on ConcurrentSkipListMap sub-map views removes only the keys in range and leaves the rest of the map intact.
 * @run main ConcurrentSkipListMapWitupTest
 */

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }

        m.subMap(2, 4).clear();
        check("[1, 4, 5]", m.keySet().toString(), "subMap(2,4).clear()");

        m.headMap(2).clear();
        check("[4, 5]", m.keySet().toString(), "headMap(2).clear()");

        m.tailMap(5).clear();
        check("[4]", m.keySet().toString(), "tailMap(5).clear()");

        m.clear();
        check("true", String.valueOf(m.isEmpty()), "clear() on full map");

        m.put(7, "x");
        m.subMap(1, 10).clear();
        check("true", String.valueOf(m.isEmpty()), "subMap covering all keys clear()");

        m.subMap(1, 10).clear();
        check("true", String.valueOf(m.isEmpty()), "clear() on empty sub-map view");
    }

    private static void check(String expected, String actual, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected " + expected + " but was " + actual);
        }
    }
}
