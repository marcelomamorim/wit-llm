/* @test    @summary Test removeIf method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.function.Predicate;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRemoveIfThrows();
    }

    public static void testRemoveIfThrows() {
        java.util.ImmutableCollections instance = new java.util.ImmutableCollections() {
            @Override
            public boolean removeIf(Predicate<? super Object> filter) {
                return super.removeIf(filter);
            }
        };
        try {
            instance.removeIf(e -> true);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
