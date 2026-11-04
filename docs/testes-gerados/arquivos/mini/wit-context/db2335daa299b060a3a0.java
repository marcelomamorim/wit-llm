/*
 * @test
 * @summary Test TreeMap.comparator() method throws InternalError as per current implementation
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Comparator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Object, Object> map = new TreeMap<>();
        try {
            Comparator<?> comp = map.comparator();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception caught, test passes
        }
    }
}
