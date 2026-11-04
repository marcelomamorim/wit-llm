/* @test    @summary Test for TreeMap.headMap method to observe exception behavior    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testHeadMapThrowsInternalError();
    }

    private static void testHeadMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            map.headMap(10);
            // Since the method always throws InternalError, reaching this line indicates failure
            System.err.println("Test failed: no exception thrown");
        } catch (InternalError e) {
            System.out.println("Caught expected InternalError");
        } catch (Throwable t) {
            System.err.println("Test failed: caught unexpected exception: " + t);
        }
    }
}
