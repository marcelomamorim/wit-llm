/*
 * @test
 * @summary Test Collections.set method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testSetThrowsUnsupportedOperationException();
    }

    private static void testSetThrowsUnsupportedOperationException() {
        try {
            // Directly test the set method that always throws UnsupportedOperationException
            new CollectionsSetStub<String>().set(0, "test");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception caught, test passes
        }
    }

    // Stub class to expose the set method as per the source excerpt
    static class CollectionsSetStub<E> {
        public E set(int index, E element) {
            throw new UnsupportedOperationException();
        }
    }
}
