/*
 * @test
 * @summary Test for java.util.Collections.add method
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        try {
            Collections.add("test element");
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
            System.out.println("UnsupportedOperationException correctly thrown.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
