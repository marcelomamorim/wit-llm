/*
 * @test
 * @summary Test TreeMap.entrySet() method throws InternalError as per current implementation
 * @run main TreeMapWitupTest
 */

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.entrySet();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception, test passes
        }
    }
}
