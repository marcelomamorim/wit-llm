/*
 * @test
 * @summary Test for Collections.set method to verify exception throwing behavior.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetThrowsUnsupportedOperationException();
    }

    public static void testSetThrowsUnsupportedOperationException() {
        try {
            Collections.set("testValue");
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
