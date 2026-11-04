/*
 * @test
 * @summary Test for Collections.replace method to observe behavior and exception handling.
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
            System.out.println("Test failed: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("Test failed: Caught unexpected exception: " + t);
        }
    }
}
