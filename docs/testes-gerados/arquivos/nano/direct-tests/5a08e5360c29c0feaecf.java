/* @test    @summary Test replaceAll method in Collections class    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllThrowsException();
    }

    public static void testReplaceAllThrowsException() {
        try {
            Collections.replaceAll(null, null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
