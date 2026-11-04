/* @test    @summary Test for TreeMap.entrySet() method to observe behavior and exception throwing    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Set;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEntrySetThrowsInternalError();
    }

    private static void testEntrySetThrowsInternalError() {
        TreeMap<Integer, String> map = new TreeMap<>();
        try {
            Set<?> entrySet = map.entrySet();
            // Since the method always throws InternalError, reaching this line indicates failure
            System.err.println("Expected InternalError was not thrown.");
        } catch (InternalError e) {
            System.out.println("Caught expected InternalError from entrySet(): " + e.getClass().getName());
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t.getClass().getName());
        }
    }
}
