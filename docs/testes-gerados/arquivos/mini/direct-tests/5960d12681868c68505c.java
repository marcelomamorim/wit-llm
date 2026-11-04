/*
 * @test
 * @summary Test that Collections.remove() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testRemoveThrows();
    }

    void testRemoveThrows() {
        java.util.Collections c = new java.util.Collections() {
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            c.remove();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
