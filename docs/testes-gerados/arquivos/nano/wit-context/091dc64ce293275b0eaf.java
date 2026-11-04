/* @test    @summary Test for ImmutableCollections.clear() method to verify it throws UnsupportedOperationException.    @run main ImmutableCollectionsWitupTest */

import java.util.ImmutableCollections;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testClearThrowsUOE();
    }

    public static void testClearThrowsUOE() {
        try {
            ImmutableCollections collections = new ImmutableCollections();
            collections.clear();
            System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: clear() threw UnsupportedOperationException as expected.");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
