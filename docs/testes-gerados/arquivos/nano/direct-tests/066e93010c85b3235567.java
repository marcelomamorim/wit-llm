/*
 * @test
 * @summary Test tailMap method of TreeMap to ensure it throws InternalError as specified.
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;
import java.util.SortedMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testTailMapThrowsInternalError();
    }

    public static void testTailMapThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        try {
            SortedMap<Integer, String> result = map.tailMap(2);
            // Since tailMap throws InternalError, this line should not be reached.
            // If it is reached, the test should fail.
            throw new AssertionError("Expected InternalError was not thrown.");
        } catch (InternalError e) {
            // Expected exception, test passes.
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
