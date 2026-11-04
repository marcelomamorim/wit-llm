/*
 * @test
 * @summary Verify TreeMap.headMap(toKey, inclusive) bounds and range checks on sub-maps
 * @run main TreeMapWitupTest
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> m = new TreeMap<>();
        for (int i = 1; i <= 10; i++) {
            m.put(i, "v" + i);
        }

        check(m.headMap(5, false).keySet().equals(set(1, 2, 3, 4)), "exclusive headMap");
        check(m.headMap(5, true).keySet().equals(set(1, 2, 3, 4, 5)), "inclusive headMap");
        check(m.headMap(0, true).isEmpty(), "headMap below all keys");
        check(m.headMap(0, false).isEmpty(), "headMap exclusive below all keys");

        NavigableMap<Integer, String> sub = m.subMap(3, true, 7, true);
        check(sub.headMap(5, true).keySet().equals(set(3, 4, 5)), "sub inclusive headMap");
        check(sub.headMap(5, false).keySet().equals(set(3, 4)), "sub exclusive headMap");
        check(sub.headMap(3, false).isEmpty(), "sub headMap at lower bound exclusive");
        check(sub.headMap(7, true).keySet().equals(set(3, 4, 5, 6, 7)), "sub headMap at upper bound");

        expectIAE(sub, 8, true);
        expectIAE(sub, 8, false);
        expectIAE(sub, 2, true);
        expectIAE(sub, 2, false);

        System.out.println("PASS");
    }

    static Set<Integer> set(Integer... xs) {
        return new HashSet<>(Arrays.asList(xs));
    }

    static void expectIAE(NavigableMap<Integer, String> map, int key, boolean inclusive) {
        try {
            map.headMap(key, inclusive);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for key " + key + " inclusive=" + inclusive);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
