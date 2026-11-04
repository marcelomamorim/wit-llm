/*
 * @test
 * @summary Test that Collections.replace throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

public class CollectionsWitupTest {
    public static void main(String[] args) {
        CollectionsWitupTest test = new CollectionsWitupTest();
        test.testReplaceThrows();
    }

    private void testReplaceThrows() {
        java.util.Collections.ReplaceMap<String, String> map = new java.util.Collections.ReplaceMap<>();
        try {
            map.replace("key", "value");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }

    // Minimal implementation to access replace method
    private static class ReplaceMap<K, V> extends java.util.AbstractMap<K, V> {
        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public java.util.Set<java.util.Map.Entry<K, V>> entrySet() {
            return java.util.Collections.emptySet();
        }
    }
}
