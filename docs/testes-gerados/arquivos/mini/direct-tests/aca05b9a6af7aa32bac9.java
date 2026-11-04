/*
 * @test
 * @summary Test PropertyResourceBundle.handleGetObject behavior
 * @run main PropertyResourceBundleWitupTest
 */

import java.util.PropertyResourceBundle;
import java.io.StringReader;
import java.io.IOException;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws IOException {
        testHandleGetObjectReturnsValue();
        testHandleGetObjectReturnsNullForMissingKey();
        testHandleGetObjectThrowsNPEForNullKey();
    }

    private static void testHandleGetObjectReturnsValue() throws IOException {
        String props = "key1=value1\nkey2=value2";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        Object val1 = bundle.handleGetObject("key1");
        if (!"value1".equals(val1)) {
            throw new AssertionError("Expected 'value1' for key1 but got: " + val1);
        }
        Object val2 = bundle.handleGetObject("key2");
        if (!"value2".equals(val2)) {
            throw new AssertionError("Expected 'value2' for key2 but got: " + val2);
        }
    }

    private static void testHandleGetObjectReturnsNullForMissingKey() throws IOException {
        String props = "key1=value1";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        Object val = bundle.handleGetObject("missingKey");
        if (val != null) {
            throw new AssertionError("Expected null for missing key but got: " + val);
        }
    }

    private static void testHandleGetObjectThrowsNPEForNullKey() throws IOException {
        String props = "key1=value1";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        try {
            bundle.handleGetObject(null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
