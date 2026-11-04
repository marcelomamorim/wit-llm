/*
 * @test
 * @summary Test PropertyResourceBundle.handleGetObject(String) for null key and valid key
 * @run main PropertyResourceBundleWitupTest
 */

import java.util.PropertyResourceBundle;
import java.io.StringReader;
import java.io.IOException;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws IOException {
        // Prepare a minimal properties content
        String props = "key1=value1\n";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));

        // Test 1: handleGetObject(null) should throw NullPointerException
        try {
            bundle.handleGetObject(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: handleGetObject with existing key returns correct value
        Object val = bundle.handleGetObject("key1");
        if (!"value1".equals(val)) {
            throw new RuntimeException("Expected value1 for key1, got: " + val);
        }

        // Test 3: handleGetObject with non-existing key returns null
        Object val2 = bundle.handleGetObject("nonexistent");
        if (val2 != null) {
            throw new RuntimeException("Expected null for nonexistent key, got: " + val2);
        }
    }
}
