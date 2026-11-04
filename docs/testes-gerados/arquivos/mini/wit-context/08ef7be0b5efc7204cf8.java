/*
 * @test
 * @summary Test TreeMap.firstKey() method throws InternalError as per current implementation
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Object, Object> map = new TreeMap<>();
        try {
            map.firstKey();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception caught, test passes
        }
    }
}
