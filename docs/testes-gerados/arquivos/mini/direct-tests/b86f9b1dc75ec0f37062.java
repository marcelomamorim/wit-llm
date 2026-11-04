/*
 * @test
 * @summary Test Collections.remove(Object key, Object value) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Collections c = null; // Collections is a utility class, no instance needed
        boolean thrown = false;
        try {
            // The method is static, call directly
            java.util.Collections.remove("key", "value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        }
    }
}
