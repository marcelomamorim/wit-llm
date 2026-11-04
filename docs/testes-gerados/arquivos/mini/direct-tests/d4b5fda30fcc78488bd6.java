/*
 * @test
 * @summary Test Collections.set(int, E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        java.util.Collections c = new java.util.Collections() { // anonymous subclass to call set
            @Override
            public Object set(int index, Object element) {
                return super.set(index, element);
            }
        };

        boolean thrown = false;
        try {
            c.set(0, "test");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        }
    }
}
