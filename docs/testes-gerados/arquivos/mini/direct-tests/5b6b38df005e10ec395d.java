/*
 * @test
 * @summary Test that Collections.replace throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Collections collections = new java.util.Collections() {
            @Override
            public boolean replace(Object key, Object oldValue, Object newValue) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            collections.replace("key", "old", "new");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
