/*
 * @test
 * @summary Verify ConcurrentSkipListMap entrySet().removeIf on the full map and on a headMap view, including null filter handling
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<Integer> keys(Map<Integer, String> map) {
        return new ArrayList<>(map.keySet());
    }

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, "v" + i);
        }

        boolean removed = map.entrySet().removeIf(e -> e.getKey() % 2 == 0);
        check(removed, "removeIf should report removal of even keys");
        check(map.size() == 5, "expected 5 entries after removal, got " + map.size());
        check(keys(map).equals(Arrays.asList(1, 3, 5, 7, 9)),
              "unexpected keys after removeIf: " + keys(map));

        boolean removedNone = map.entrySet().removeIf(e -> e.getKey() > 100);
        check(!removedNone, "removeIf should return false when nothing matches");
        check(map.size() == 5, "map size changed when nothing matched");

        boolean nullFilterThrew = false;
        try {
            map.entrySet().removeIf(null);
        } catch (NullPointerException expected) {
            nullFilterThrew = true;
        }
        check(nullFilterThrew, "removeIf(null) should throw NullPointerException");
        check(map.size() == 5, "map changed after removeIf(null)");

        ConcurrentSkipListMap<Integer, String> map2 = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 10; i++) {
            map2.put(i, "v" + i);
        }
        boolean subRemoved = map2.headMap(5).entrySet().removeIf(e -> e.getKey() % 2 == 0);
        check(subRemoved, "headMap removeIf should report removal");
        check(keys(map2).equals(Arrays.asList(1, 3, 5, 6, 7, 8, 9)),
              "unexpected keys after headMap removeIf: " + keys(map2));
    }
}
