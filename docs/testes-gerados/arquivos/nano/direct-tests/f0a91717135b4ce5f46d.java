/*
 * @test
 * @summary Test that Collections.set throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Object dummy = new Object();
        try {
            Collections.set(dummy);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes
        }
    }
}
