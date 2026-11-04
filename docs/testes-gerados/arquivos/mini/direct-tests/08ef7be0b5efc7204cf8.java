/*
 * @test
 * @summary Test TreeMap.firstKey() method that throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.firstKey();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
