/*
 * @test
 * @summary Test TreeMap.lastKey() method throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        try {
            map.lastKey();
            // If no exception, fail the test
            throw new AssertionError("Expected InternalError was not thrown.");
        } catch (InternalError e) {
            // Expected exception, test passes
        }
    }
}
