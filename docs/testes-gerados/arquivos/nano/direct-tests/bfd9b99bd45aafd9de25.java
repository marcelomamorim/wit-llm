/* @test    @summary Test addAll method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testAddAllThrows();
    }

    static void testAddAllThrows() {
        Collection<String> empty = Collections.emptyList();
        try {
            ImmutableCollections.addAll(empty);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passes
        }
    }
}
