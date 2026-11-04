/*
 * @test
 * @summary Test that Collections.remove(int) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testRemoveThrows();
    }

    private void testRemoveThrows() {
        java.util.Collections coll = new java.util.Collections() {
            @Override
            public Object remove(int index) {
                return super.remove(index);
            }
        };

        try {
            coll.remove(0);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
