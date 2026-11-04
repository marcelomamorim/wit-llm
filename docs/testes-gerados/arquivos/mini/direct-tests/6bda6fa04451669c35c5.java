/*
 * @test
 * @summary Test TreeMap.lastKey() method throws InternalError as implemented
 * @run main java.util.TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.lastKey();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
