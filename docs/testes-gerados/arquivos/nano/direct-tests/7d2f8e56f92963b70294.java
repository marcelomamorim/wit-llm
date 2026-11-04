/* @test    @summary Test for Collections.replaceAll method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllThrows();
    }

    public static void testReplaceAllThrows() {
        try {
            Collections.replaceAll(null);
            throw new AssertionError("Expected UnsupportedOperationException for null input");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
        
        // Additional test: invoke replaceAll on a dummy object to confirm exception
        try {
            Collections.replaceAll(new Object());
            throw new AssertionError("Expected UnsupportedOperationException for Object instance");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
