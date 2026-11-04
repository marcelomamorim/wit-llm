/* @test    @summary Test retainAll method in ImmutableCollections to ensure it throws UnsupportedOperationException    @run main ImmutableCollectionsWitupTest */

import java.util.*;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRetainAllThrows();
    }

    static void testRetainAllThrows() {
        ImmutableCollections collection = new ImmutableCollections();
        Collection<Object> coll = new ArrayList<>();
        coll.add("element");
        try {
            collection.retainAll(coll);
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is thrown
        }
    }
}
