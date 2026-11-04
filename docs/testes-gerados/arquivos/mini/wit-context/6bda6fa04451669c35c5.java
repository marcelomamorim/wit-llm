/*
 * @test
 * @summary Test TreeMap.lastKey() method throws InternalError as per current implementation
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Object, Object> map = new TreeMap<>();
        try {
            map.lastKey();
            throw new AssertionError("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
