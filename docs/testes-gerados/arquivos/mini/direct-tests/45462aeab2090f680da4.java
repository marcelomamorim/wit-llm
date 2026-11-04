/*
 * @test
 * @summary Test java.util.ListResourceBundle.handleGetObject(String) method
 * @run main java.util.ListResourceBundleWitupTest
 */

import java.util.ListResourceBundle;

public class ListResourceBundleWitupTest {

    static class TestBundle extends ListResourceBundle {
        @Override
        protected Object[][] getContents() {
            return new Object[][] {
                {"key1", "value1"},
                {"key2", "value2"},
                {"nullValue", null}
            };
        }
    }

    public static void main(String[] args) {
        ListResourceBundle bundle = new TestBundle();

        // Test retrieval of existing keys
        Object v1 = bundle.handleGetObject("key1");
        if (!"value1".equals(v1)) {
            throw new RuntimeException("Expected value1 for key1, got: " + v1);
        }

        Object v2 = bundle.handleGetObject("key2");
        if (!"value2".equals(v2)) {
            throw new RuntimeException("Expected value2 for key2, got: " + v2);
        }

        // Test retrieval of key with null value
        Object vNull = bundle.handleGetObject("nullValue");
        if (vNull != null) {
            throw new RuntimeException("Expected null for key 'nullValue', got: " + vNull);
        }

        // Test retrieval of non-existing key returns null
        Object vMissing = bundle.handleGetObject("missingKey");
        if (vMissing != null) {
            throw new RuntimeException("Expected null for missing key, got: " + vMissing);
        }

        // Test that passing null key throws NullPointerException
        try {
            bundle.handleGetObject(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
