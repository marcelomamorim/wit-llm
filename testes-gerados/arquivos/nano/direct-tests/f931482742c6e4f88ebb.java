/* @test    @summary Test putIfAbsent method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentThrows();
    }

    static void testPutIfAbsentThrows() {
        try {
            ImmutableCollections.putIfAbsent("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception caught, test passes
        }
    }
}
