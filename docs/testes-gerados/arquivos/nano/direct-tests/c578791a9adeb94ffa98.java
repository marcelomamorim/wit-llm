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
            Collections.replace("key", "value");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
