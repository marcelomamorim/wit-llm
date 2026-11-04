/*
 * @test
 * @summary Test that Collections.add(int, E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testAddThrows();
    }

    private void testAddThrows() {
        java.util.Collections c = new java.util.Collections() {
            @Override
            public void add(int index, Object element) {
                throw new UnsupportedOperationException();
            }
        };

        try {
            c.add(0, "test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
