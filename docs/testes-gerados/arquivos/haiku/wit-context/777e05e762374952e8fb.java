/*
 * @test
 * @summary Verifies SubMap.containsKey bounds checks and NullPointerException for null keys via subMap view
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.NavigableMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 9; i++) {
            map.put(i, "v" + i);
        }

        NavigableMap<Integer, String> sub = map.subMap(3, true, 6, true);

        check(sub.containsKey(3));
        check(sub.containsKey(4));
        check(sub.containsKey(6));
        check(!sub.containsKey(2));
        check(!sub.containsKey(7));

        boolean thrown = false;
        try {
            sub.containsKey(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown);
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("assertion failed");
        }
    }
}
