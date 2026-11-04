/*
 * @test
 * @summary Basic behavior of ConcurrentSkipListMap.tailMap(K, boolean)
 * @run main ConcurrentSkipListMapWitupTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {
    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static List<Integer> keys(ConcurrentNavigableMap<Integer, String> m) {
        return new ArrayList<>(m.keySet());
    }

    static ConcurrentNavigableMap<Integer, String> base() {
        ConcurrentNavigableMap<Integer, String> m = new ConcurrentSkipListMap<>();
        for (int i = 1; i <= 5; i++) {
            m.put(i, "v" + i);
        }
        return m;
    }

    public static void main(String[] args) {
        ConcurrentNavigableMap<Integer, String> m = base();

        check(keys(m.tailMap(2, true)).equals(Arrays.asList(2, 3, 4, 5)),
              "inclusive tailMap from existing key");

        check(keys(m.tailMap(2, false)).equals(Arrays.asList(3, 4, 5)),
              "exclusive tailMap from existing key");

        check(keys(m.tailMap(0, true)).equals(Arrays.asList(1, 2, 3, 4, 5)),
              "tailMap from key below all entries");

        check(keys(m.tailMap(3, true)).equals(Arrays.asList(3, 4, 5)),
              "inclusive tailMap from middle key");

        check(m.tailMap(6, true).isEmpty(),
              "tailMap from key above all entries must be empty");

        check(m.tailMap(5, false).isEmpty(),
              "exclusive tailMap from largest key must be empty");

        ConcurrentNavigableMap<Integer, String> sub = m.tailMap(3, true);
        m.put(6, "v6");
        check(sub.containsKey(6), "submap view must reflect later insertions above bound");
        check(!sub.containsKey(2), "submap must not contain keys below bound");

        ConcurrentNavigableMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        check(empty.tailMap(1, true).isEmpty(), "tailMap of empty map must be empty");

        try {
            m.tailMap(null, true);
            throw new RuntimeException("expected NullPointerException for null fromKey");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
