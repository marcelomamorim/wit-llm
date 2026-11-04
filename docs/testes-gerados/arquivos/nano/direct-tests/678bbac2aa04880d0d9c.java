/* @test    @summary Test for Collections.setValue method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetValueThrowsException();
    }

    public static void testSetValueThrowsException() {
        try {
            Collections.setValue("test");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
