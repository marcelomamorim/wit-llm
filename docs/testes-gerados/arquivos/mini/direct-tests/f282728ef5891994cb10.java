/*
 * @test
 * @summary Test UnsupportedOperationException from Collections.replace
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Collections c = new java.util.Collections() {
            public Object replace(Object key, Object value) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            c.replace("key", "value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
