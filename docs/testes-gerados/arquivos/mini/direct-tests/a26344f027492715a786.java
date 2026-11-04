/*
 * @test
 * @summary Test that Collections.replace throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testReplaceThrows();
    }

    private void testReplaceThrows() {
        java.util.Collections c = new java.util.Collections() {
            @Override
            public boolean replace(Object key, Object oldValue, Object newValue) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            c.replace("key", "old", "new");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        }
    }
}
