/* @test    @summary Test for Collections.remove(Object o) method to ensure it throws UnsupportedOperationException.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Object obj = new Object();
        try {
            Collections.remove(obj);
            // If no exception is thrown, the test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
