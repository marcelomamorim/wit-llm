/*
 * @test
 * @summary Test for Collections.replace method to ensure it throws UnsupportedOperationException.
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceMethodThrows();
    }

    public static void testReplaceMethodThrows() {
        boolean threw = false;
        try {
            // Call replace with dummy generic types, as method is static and generic
            Collections.replace("key", "oldValue", "newValue");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
