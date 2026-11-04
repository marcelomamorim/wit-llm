/* @test    @summary Test for Collections.putIfAbsent method to ensure it throws UnsupportedOperationException as specified.    @run main CollectionsWitupTest */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentThrows();
    }

    public static void testPutIfAbsentThrows() {
        try {
            Collections.putIfAbsent("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        }
    }
}
