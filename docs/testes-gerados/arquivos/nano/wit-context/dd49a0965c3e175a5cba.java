/*
 * @test
 * @summary Test for ImmutableCollections.set(E e) method.
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        testSetMethodThrowsUOE();
    }

    private static void testSetMethodThrowsUOE() {
        ImmutableCollections instance = new ImmutableCollections();
        boolean threwException = false;
        try {
            instance.set(null);
        } catch (UnsupportedOperationException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
