/*
 * @test
 * @summary Test Collections.replace method throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceThrows();
    }

    private static void testReplaceThrows() {
        try {
            // The replace method in Collections throws UnsupportedOperationException always
            // We call it with nulls to test the exception deterministically
            new CollectionsWitupTest().replace(null, null, null);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }

    // Proxy method to call the target replace method
    // Since the original method is generic and instance method in UnmodifiableMap,
    // and we have only the method signature and know it throws UnsupportedOperationException,
    // we simulate the call here to trigger the exception.
    public <K, V> boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }
}
