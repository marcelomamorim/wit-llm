/* @test    @summary Test headMap method of TreeMap throws InternalError as specified    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapThrowsInternalError();
    }

    public static void testHeadMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            map.headMap(2);
        } catch (InternalError e) {
            // Expected exception
            return;
        }
        throw new AssertionError("Expected InternalError was not thrown");
    }
}
