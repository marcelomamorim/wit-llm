/*
 * @test
 * @summary Test Collections.remove(Object key, Object value) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            java.util.Collections.remove("key", "value");
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
