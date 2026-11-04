/*
 * @test
 * @summary Test Collections.add method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Map;
import java.util.AbstractMap;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddMethodThrows();
    }

    public static void testAddMethodThrows() {
        Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>("key", "value");
        try {
            boolean result = Collections.add(entry);
            // Since the method always throws, this line should not be reached.
            System.err.println("Expected UnsupportedOperationException, but method returned: " + result);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes.
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
