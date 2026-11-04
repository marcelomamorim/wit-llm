/*
 * @test
 * @summary Test TreeMap.headMap(K) method that throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.headMap(10);
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception caught, test passes
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception type: " + t, t);
        }
    }
}
