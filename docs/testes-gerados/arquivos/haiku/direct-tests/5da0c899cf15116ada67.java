/*
 * @test
 * @summary Verifies that TreeMap.subMap(K, K) currently throws InternalError for observable inputs
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>();
        map.put("a", 1);
        map.put("c", 3);

        boolean thrown = false;
        try {
            map.subMap("a", "b");
        } catch (InternalError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("subMap(fromKey, toKey) did not throw InternalError");
        }

        TreeMap<String, Integer> reversed = new TreeMap<>(java.util.Comparator.reverseOrder());
        reversed.put("x", 10);
        thrown = false;
        try {
            reversed.subMap("z", "a");
        } catch (InternalError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("subMap on comparator-based map did not throw InternalError");
        }
    }
}
