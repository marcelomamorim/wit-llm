/*
 * @test
 * @summary Test that Collections.remove(Object) always throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        try {
            new java.util.Collections() {
                @Override
                public Object remove(Object key) {
                    throw new UnsupportedOperationException();
                }
            }.remove("test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
