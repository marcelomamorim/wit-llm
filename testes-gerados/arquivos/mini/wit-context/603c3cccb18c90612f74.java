/*
 * @test
 * @summary Test KeyValueHolder.setValue throws UnsupportedOperationException
 * @run main java.util.KeyValueHolderWitupTest
 */

import java.util.KeyValueHolder;
import java.util.Map;

public class KeyValueHolderWitupTest {
    public static void main(String[] args) {
        // Create a KeyValueHolder instance with non-null key and value
        KeyValueHolder<String, String> holder = new KeyValueHolder<>("key", "value");

        // Verify getKey and getValue return the expected values
        if (!"key".equals(holder.getKey())) {
            throw new AssertionError("getKey() returned unexpected value");
        }
        if (!"value".equals(holder.getValue())) {
            throw new AssertionError("getValue() returned unexpected value");
        }

        // Test that setValue throws UnsupportedOperationException
        try {
            holder.setValue("newValue");
            throw new AssertionError("setValue did not throw UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }

        // Test equals and hashCode consistency
        KeyValueHolder<String, String> sameHolder = new KeyValueHolder<>("key", "value");
        if (!holder.equals(sameHolder)) {
            throw new AssertionError("equals() failed for equal entries");
        }
        if (holder.hashCode() != sameHolder.hashCode()) {
            throw new AssertionError("hashCode() inconsistent for equal entries");
        }

        // Test equals with a Map.Entry implementation with same key and value
        Map.Entry<String, String> entry = new Map.Entry<String, String>() {
            public String getKey() { return "key"; }
            public String getValue() { return "value"; }
            public String setValue(String v) { throw new UnsupportedOperationException(); }
        };
        if (!holder.equals(entry)) {
            throw new AssertionError("equals() failed for Map.Entry with same key and value");
        }

        // Test equals with null and unrelated object
        if (holder.equals(null)) {
            throw new AssertionError("equals() should return false for null");
        }
        if (holder.equals("some string")) {
            throw new AssertionError("equals() should return false for unrelated object");
        }

        // Test toString returns expected format
        String toString = holder.toString();
        if (!toString.contains("key") || !toString.contains("value")) {
            throw new AssertionError("toString() does not contain key and value");
        }
    }
}
