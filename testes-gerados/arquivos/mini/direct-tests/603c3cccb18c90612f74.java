/*
 * @test
 * @summary Test KeyValueHolder.setValue throws UnsupportedOperationException
 * @run main KeyValueHolderWitupTest
 */

import java.util.Map;
import java.util.Objects;

public class KeyValueHolderWitupTest {

    static final class KeyValueHolder<K, V> implements Map.Entry<K, V> {
        final K key;
        final V value;

        KeyValueHolder(K k, V v) {
            key = Objects.requireNonNull(k);
            value = Objects.requireNonNull(v);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("not supported");
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return key.equals(e.getKey()) && value.equals(e.getValue());
        }

        @Override
        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    public static void main(String[] args) {
        KeyValueHolder<String, String> holder = new KeyValueHolder<>("key", "value");

        // Test that setValue throws UnsupportedOperationException
        try {
            holder.setValue("newValue");
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Test getKey and getValue return expected values
        if (!"key".equals(holder.getKey())) {
            throw new RuntimeException("getKey() returned unexpected value");
        }
        if (!"value".equals(holder.getValue())) {
            throw new RuntimeException("getValue() returned unexpected value");
        }

        // Test equals with another Map.Entry with same key and value
        Map.Entry<String, String> other = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "value"; }
            public String setValue(String v) { throw new UnsupportedOperationException(); }
        };
        if (!holder.equals(other)) {
            throw new RuntimeException("equals() failed for equal entries");
        }

        // Test equals with null and unrelated object
        if (holder.equals(null)) {
            throw new RuntimeException("equals() should return false for null");
        }
        if (holder.equals("some string")) {
            throw new RuntimeException("equals() should return false for unrelated object");
        }

        // Test hashCode consistency
        int expectedHash = "key".hashCode() ^ "value".hashCode();
        if (holder.hashCode() != expectedHash) {
            throw new RuntimeException("hashCode() returned unexpected value");
        }

        // Test toString returns key=value
        if (!"key=value".equals(holder.toString())) {
            throw new RuntimeException("toString() returned unexpected value");
        }
    }
}
