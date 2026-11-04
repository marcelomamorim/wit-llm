/*
 * @test
 * @summary Test TreeMap.entrySet() throws InternalError as implemented
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Set;
import java.util.Map;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            Set<Map.Entry<String, String>> entries = map.entrySet();
            throw new Error("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
