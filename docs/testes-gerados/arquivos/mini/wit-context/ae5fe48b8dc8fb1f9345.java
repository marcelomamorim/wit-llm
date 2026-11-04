/*
 * @test
 * @summary Test Collections.remove(Object) method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testRemoveThrows();
    }

    private static void testRemoveThrows() {
        try {
            // The remove method always throws UnsupportedOperationException
            java.util.Collections.remove(new Object());
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
