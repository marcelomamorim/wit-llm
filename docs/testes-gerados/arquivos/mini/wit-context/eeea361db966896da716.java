/*
 * @test
 * @summary Test Collections.SingletonMap.putIfAbsent throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentUnsupportedOperationException();
    }

    private static void testPutIfAbsentUnsupportedOperationException() {
        java.util.Collections.SingletonMap<String, String> singletonMap =
            new java.util.Collections.SingletonMap<>("key", "value");
        try {
            singletonMap.putIfAbsent("key", "newValue");
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
