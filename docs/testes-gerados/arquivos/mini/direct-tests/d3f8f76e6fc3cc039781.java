/*
 * @test
 * @summary Test that ImmutableCollections.remove(Object) throws UnsupportedOperationException
 * @run main ImmutableCollectionsWitupTest
 */

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        try {
            // Create an anonymous subclass to call remove since ImmutableCollections is package-private
            java.util.ImmutableCollections ic = new java.util.ImmutableCollections() {
                @Override
                public Object remove(Object key) {
                    return super.remove(key);
                }
            };
            ic.remove("test");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
