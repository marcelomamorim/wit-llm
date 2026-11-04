/*
 * @test
 * @summary Test Collections.UnmodifiableMap.put throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testUnmodifiableMapPutThrows();
    }

    static void testUnmodifiableMapPutThrows() {
        java.util.Map<String, String> unmodifiableMap = java.util.Collections.unmodifiableMap(new java.util.HashMap<>());
        try {
            unmodifiableMap.put("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
