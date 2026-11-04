/*
 * @test
 * @summary Verifies that TreeMap.tailMap(K) throws InternalError as in the provided source, and leaves the map unchanged
 * @run main TreeMapWitupTest
 */

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "a");
        map.put(2, "b");

        checkThrowsInternalError(map, 1);
        checkThrowsInternalError(map, 2);
        checkThrowsInternalError(map, 3);

        if (map.size() != 2) {
            throw new RuntimeException("Map size changed: " + map.size());
        }
        if (!"a".equals(map.get(1)) || !"b".equals(map.get(2))) {
            throw new RuntimeException("Map contents changed");
        }
    }

    private static void checkThrowsInternalError(TreeMap<Integer, String> map, Integer key) {
        boolean thrown = false;
        try {
            SortedMap<Integer, String> result = map.tailMap(key);
            if (result == null) {
                thrown = false;
            }
        } catch (InternalError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected InternalError from tailMap(" + key + ")");
        }
    }
}
