/*
 * @test
 * @summary Test that ImmutableCollections.remove(Object key, Object value) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        ImmutableCollections ic = new ImmutableCollections() { };
        try {
            ic.remove("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
