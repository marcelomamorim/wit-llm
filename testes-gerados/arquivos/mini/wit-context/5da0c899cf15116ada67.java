/*
 * @test
 * @summary Test TreeMap.subMap(K fromKey, K toKey) throws InternalError as implemented
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.subMap(1, 2);
            throw new Error("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
