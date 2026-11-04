/*
 * @test
 * @summary Test that TreeMap.subMap(K fromKey, K toKey) throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");

        try {
            SortedMap<String, String> sub = map.subMap("a", "c");
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
