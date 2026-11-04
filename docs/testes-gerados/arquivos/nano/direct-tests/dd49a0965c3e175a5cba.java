/* @test    @summary Test for ImmutableCollections.set method to ensure it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testSetMethodThrows();
    }

    public static void testSetMethodThrows() {
        ImmutableCollections ic = new ImmutableCollections();
        boolean threw = false;
        try {
            ic.set("test");
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown by set()");
        }
    }
}
