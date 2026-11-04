/*
 * @test
 * @summary Test PropertyResourceBundle constructor with Reader
 * @run main PropertyResourceBundleWitupTest
 */

import java.util.PropertyResourceBundle;
import java.io.StringReader;
import java.io.IOException;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws IOException {
        testValidProperties();
        testEmptyProperties();
        testMalformedProperties();
    }

    private static void testValidProperties() throws IOException {
        String props = "key1=value1\nkey2=value2";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        Object val1 = bundle.handleGetObject("key1");
        Object val2 = bundle.handleGetObject("key2");
        if (!"value1".equals(val1)) {
            throw new AssertionError("Expected value1 for key1 but got " + val1);
        }
        if (!"value2".equals(val2)) {
            throw new AssertionError("Expected value2 for key2 but got " + val2);
        }
    }

    private static void testEmptyProperties() throws IOException {
        String props = "";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        Object val = bundle.handleGetObject("anyKey");
        if (val != null) {
            throw new AssertionError("Expected null for anyKey in empty properties but got " + val);
        }
    }

    private static void testMalformedProperties() {
        String props = "keyWithoutValue\nkey2=value2";
        try {
            new PropertyResourceBundle(new StringReader(props));
            throw new AssertionError("Expected IOException for malformed properties but none thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
