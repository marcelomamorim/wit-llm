/* @test    @summary Test for Collections.put method to ensure it throws UnsupportedOperationException    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutMethodThrows();
    }

    public static void testPutMethodThrows() {
        try {
            Collections.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
