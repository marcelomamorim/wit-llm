/* @test    @summary Test TreeMap.entrySet() method for throwing InternalError.    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.Set;
import java.util.Map;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        
        try {
            Set<Map.Entry<Integer, String>> entries = map.entrySet();
            // Expecting InternalError to be thrown
            // If no exception, fail the test
            System.err.println("Expected InternalError was not thrown.");
            System.exit(1);
        } catch (InternalError e) {
            // Test passes if InternalError is caught
            System.out.println("InternalError caught as expected.");
            System.exit(0);
        } catch (Throwable t) {
            // Any other exception means test failure
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
