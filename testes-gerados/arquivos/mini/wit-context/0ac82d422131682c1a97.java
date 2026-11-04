/*
 * @test
 * @summary Test PropertyResourceBundle(Reader) constructor for normal and error cases
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
        // Check that keys are present and values match
        if (!"value1".equals(bundle.getString("key1"))) {
            throw new AssertionError("Expected value1 for key1");
        }
        if (!"value2".equals(bundle.getString("key2"))) {
            throw new AssertionError("Expected value2 for key2");
        }
    }

    private static void testEmptyProperties() throws IOException {
        String props = "";
        PropertyResourceBundle bundle = new PropertyResourceBundle(new StringReader(props));
        // The bundle should have no keys
        if (bundle.getKeys().hasMoreElements()) {
            throw new AssertionError("Expected no keys in empty properties");
        }
    }

    private static void testMalformedProperties() {
        // According to Properties.load(Reader), malformed input throws IOException
        // Provide a malformed properties string (e.g. a line with no '=' or ':' and no key)
        String malformed = "\u0000"; // invalid character to provoke IOException
        try {
            new PropertyResourceBundle(new StringReader(malformed));
            throw new AssertionError("Expected IOException for malformed properties");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
