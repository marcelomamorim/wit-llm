/*
 * @test
 * @summary Test Collections.remove() throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Collections c = new java.util.Collections() {
            @Override
            public boolean remove(Object o) {
                return super.remove(o);
            }
        };

        boolean thrown = false;
        try {
            c.remove("test");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
