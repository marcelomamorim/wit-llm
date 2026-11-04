/*
 * @test
 * @summary Test PropertyResourceBundle(InputStream) constructor for basic functionality and error handling
 * @run main PropertyResourceBundleWitupTest
 */

import java.util.PropertyResourceBundle;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws IOException {
        testValidInputStream();
        testNullInputStream();
    }

    private static void testValidInputStream() throws IOException {
        // Prepare a simple properties content in ISO-8859-1 encoding
        String props = "key1=value1\nkey2=value2";
        byte[] bytes = props.getBytes("ISO-8859-1");
        InputStream is = new ByteArrayInputStream(bytes);

        PropertyResourceBundle bundle = new PropertyResourceBundle(is);

        // Check that keys return expected values
        if (!"value1".equals(bundle.getString("key1"))) {
            throw new AssertionError("Expected value1 for key1");
        }
        if (!"value2".equals(bundle.getString("key2"))) {
            throw new AssertionError("Expected value2 for key2");
        }
    }

    private static void testNullInputStream() {
        // The constructor does not explicitly check for null and will throw NullPointerException
        // when trying to create InputStreamReader with null stream.
        try {
            new PropertyResourceBundle((InputStream) null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // Expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException for null InputStream");
        }
    }
}
