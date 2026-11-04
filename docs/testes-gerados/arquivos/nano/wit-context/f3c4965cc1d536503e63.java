/* @test    @summary Test for ImmutableCollections.retainAll method to verify exception throwing behavior    @run main ImmutableCollectionsWitupTest */

import java.util.Collection;
import java.util.Collections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testRetainAllThrowsUOE();
    }

    private static void testRetainAllThrowsUOE() {
        ImmutableCollections instance = new ImmutableCollections();
        Collection<Object> dummyCollection = Collections.emptyList();
        try {
            boolean result = instance.retainAll(dummyCollection);
            if (result != false) {
                throw new AssertionError("Expected retainAll to throw UnsupportedOperationException, but it returned: " + result);
            }
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
