/*
 * @test
 * @summary Test UnsupportedOperationException from clear() method in Collections
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testClearThrows();
    }

    private void testClearThrows() {
        java.util.Collections coll = new java.util.Collections() {
            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }
        };

        try {
            coll.clear();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
