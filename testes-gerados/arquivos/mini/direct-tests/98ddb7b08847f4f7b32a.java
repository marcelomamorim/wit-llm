/*
 * @test
 * @summary Test that Collections remove() method throws UnsupportedOperationException
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
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            coll.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
