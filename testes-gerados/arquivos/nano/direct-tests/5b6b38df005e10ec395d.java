/* @test    @summary Test for Collections.replace method to ensure it throws UnsupportedOperationException as expected.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceMethodThrowsException();
    }

    public static void testReplaceMethodThrowsException() {
        boolean exceptionThrown = false;
        try {
            Collections.replace("key", "oldValue", "newValue");
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
