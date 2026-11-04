/*
 * @test
 * @summary Witup-derived behavioral checks for ConcurrentSkipListMap.lowerEntry
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, Integer> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, i * 10);
        }

        Map.Entry<Integer, Integer> e = m.lowerEntry(3);
        if (e == null || e.getKey().intValue() != 2 || e.getValue().intValue() != 20) {
            throw new AssertionError();
        }

        e = m.lowerEntry(6);
        if (e == null || e.getKey().intValue() != 5 || e.getValue().intValue() != 50) {
            throw new AssertionError();
        }

        if (m.lowerEntry(1) != null) {
            throw new AssertionError();
        }

        try {
            m.lowerEntry(null);
            throw new AssertionError();
        } catch (NullPointerException ex) {
            // expected
        }
    }
}
