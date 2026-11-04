/*
 * @test
 * @summary Test Collections.UnmodifiableCollection.add(E) throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testAddUnsupportedOperationException();
    }

    static void testAddUnsupportedOperationException() {
        java.util.Collection<String> unmodifiable = java.util.Collections.unmodifiableCollection(new java.util.ArrayList<>());
        boolean thrown = false;
        try {
            unmodifiable.add("test");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by add method");
        }
    }
}
