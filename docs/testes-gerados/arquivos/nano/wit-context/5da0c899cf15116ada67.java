/* @test    @summary Test for TreeMap.subMap method to observe behavior and exception handling    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testSubMapThrowsInternalError();
        testSubMapNormalBehavior();
    }

    private static void testSubMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        // No entries added, but subMap is expected to throw InternalError
        try {
            SortedMap<Integer, String> sub = map.subMap(1, 10);
            // If no exception, fail the test
            System.err.println("Expected InternalError was not thrown.");
            System.exit(1);
        } catch (InternalError e) {
            // Expected exception caught
            System.out.println("InternalError correctly thrown by subMap.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    private static void testSubMapNormalBehavior() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            SortedMap<Integer, String> sub = map.subMap(1, 3);
            // Since the method throws InternalError, this code is not expected to reach here
            System.err.println("subMap did not throw as expected.");
            System.exit(1);
        } catch (InternalError e) {
            System.out.println("subMap threw InternalError as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
