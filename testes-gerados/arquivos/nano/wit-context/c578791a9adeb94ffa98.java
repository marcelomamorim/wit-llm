/*
 * @test
 * @summary Test for Collections.replace method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrowsUnsupportedOperationException();
    }

    public static void testReplaceThrowsUnsupportedOperationException() {
        try {
            Collections.replace("key", "value");
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Caught unexpected exception: " + t);
            System.exit(1);
        }
    }
}
