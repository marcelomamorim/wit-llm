/*
 * @test
 * @summary Test that Collections.set(E) always throws IllegalStateException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testSetThrows();
    }

    private void testSetThrows() {
        // Create an anonymous subclass of Collections with the set method
        Collections c = new Collections() {
            @Override
            public void set(Object e) {
                throw new IllegalStateException();
            }
        };

        try {
            c.set(null);
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }

        try {
            c.set("test");
            throw new RuntimeException("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
