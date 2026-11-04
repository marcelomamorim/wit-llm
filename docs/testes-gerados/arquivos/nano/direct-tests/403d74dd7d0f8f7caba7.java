/*
 * @test
 * @summary Test that ImmutableCollections.remove(int) always throws UnsupportedOperationException
 * @run main ImmutableCollectionsRemoveTest
 */

import java.util.ImmutableCollections;

public class ImmutableCollectionsRemoveTest {
    public static void main(String[] args) {
        testRemoveThrows();
    }

    static void testRemoveThrows() {
        try {
            ImmutableCollections col = new ImmutableCollections();
            col.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }
}
