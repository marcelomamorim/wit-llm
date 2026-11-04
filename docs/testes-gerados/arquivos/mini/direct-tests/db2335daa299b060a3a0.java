/*
 * @test
 * @summary Test TreeMap.comparator() method throws InternalError as implemented
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.Comparator;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, String> map = new TreeMap<>();
        try {
            Comparator<? super String> comp = map.comparator();
            throw new RuntimeException("Expected InternalError not thrown");
        } catch (InternalError e) {
            // Expected exception
        }
    }
}
