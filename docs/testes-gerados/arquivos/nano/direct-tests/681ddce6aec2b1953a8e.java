/* @test    @summary Test for ImmutableCollections.merge method to ensure it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.*;
import java.util.function.BiFunction;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testMergeThrows();
    }

    public static void testMergeThrows() {
        try {
            ImmutableCollections instance = new ImmutableCollections();
            instance.merge(null, null, null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
