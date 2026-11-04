/*
 * @test
 * @summary Test TreeMap.headMap(K) throws InternalError as declared
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("a", "alpha");
        map.put("b", "beta");
        map.put("c", "gamma");

        try {
            SortedMap<String, String> head = map.headMap("b");
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
