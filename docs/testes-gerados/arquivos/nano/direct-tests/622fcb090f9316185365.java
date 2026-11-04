/*
 * @test    @summary Test for Collections.add method to ensure it throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            Collections.add("test-element");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
