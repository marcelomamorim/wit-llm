/* @test    @summary Test replaceAll method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;
import java.util.function.UnaryOperator;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrows();
    }

    static void testReplaceAllThrows() {
        try {
            ImmutableCollections.replaceAll(null);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
