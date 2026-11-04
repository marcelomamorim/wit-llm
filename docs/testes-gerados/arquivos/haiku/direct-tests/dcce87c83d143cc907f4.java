/*
 * @test
 * @summary tailMap(K) returns an inclusive live view of keys greater than or equal to fromKey
 * @run main ConcurrentSkipListMapWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest {

    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "a");
        map.put(3, "c");
        map.put(5, "e");
        map.put(7, "g");

        check(keys(map.tailMap(3)), list(3, 5, 7), "tailMap(3)");
        check(keys(map.tailMap(4)), list(5, 7), "tailMap(4)");
        check(keys(map.tailMap(0)), list(1, 3, 5, 7), "tailMap(0)");
        check(keys(map.tailMap(7)), list(7), "tailMap(7)");
        check(keys(map.tailMap(8)), list(), "tailMap(8)");

        ConcurrentNavigableMap<Integer, String> view = map.tailMap(3);
        checkBool(view.firstKey().equals(3), "firstKey of tailMap(3)");
        checkBool(view.lastKey().equals(7), "lastKey of tailMap(3)");

        map.put(9, "i");
        check(keys(view), list(3, 5, 7, 9), "live view after put");

        view.put(4, "d");
        checkBool(map.containsKey(4), "put through view reaches backing map");
        check(keys(map.tailMap(3)), list(3, 4, 5, 7, 9), "tailMap(3) after put through view");

        ConcurrentSkipListMap<Integer, String> rev =
                new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        rev.put(1, "a");
        rev.put(3, "c");
        rev.put(5, "e");
        rev.put(7, "g");
        check(keys(rev.tailMap(3)), list(3, 1), "reverse comparator tailMap(3)");

        ConcurrentSkipListMap<Integer, String> empty = new ConcurrentSkipListMap<>();
        checkBool(empty.tailMap(1).isEmpty(), "tailMap on empty map is empty");
    }

    static List<Integer> keys(Map<Integer, String> m) {
        List<Integer> result = new ArrayList<>();
        for (Integer k : m.keySet()) {
            result.add(k);
        }
        return result;
    }

    static List<Integer> list(Integer... xs) {
        return Arrays.asList(xs);
    }

    static void check(List<Integer> actual, List<Integer> expected, String name) {
        if (!actual.equals(expected)) {
            throw new RuntimeException("mismatch in " + name + ": actual=" + actual
                    + " expected=" + expected);
        }
    }

    static void checkBool(boolean condition, String name) {
        if (!condition) {
            throw new RuntimeException("failed: " + name);
        }
    }
}
