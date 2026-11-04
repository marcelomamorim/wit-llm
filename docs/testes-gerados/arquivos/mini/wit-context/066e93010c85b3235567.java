/*
 * @test
 * @summary Test TreeMap.tailMap(K) method that throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.tailMap(1);
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
