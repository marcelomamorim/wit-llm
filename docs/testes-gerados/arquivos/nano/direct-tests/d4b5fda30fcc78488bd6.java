/* @test    @summary Test for Collections.set method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetMethodThrows();
    }

    public static void testSetMethodThrows() {
        int index = 0;
        String element = "test";
        try {
            Collections.set(index, element);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
