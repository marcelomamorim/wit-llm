/* @test    @summary Test for Collections.remove(int index) method to verify UnsupportedOperationException is thrown.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        try {
            Collections.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
