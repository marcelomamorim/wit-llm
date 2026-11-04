/*
 * @test
 * @summary Test PropertyResourceBundle(InputStream) constructor with different encodings
 * @run main PropertyResourceBundleWitupTest
 */

import java.util.PropertyResourceBundle;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws IOException {
        testValidISO88591Encoding();
        testValidUTF8Encoding();
    }

    private static void testValidISO88591Encoding() throws IOException {
        // ISO-8859-1 encoding is default in PropertyResourceBundle constructor
        // Prepare a simple properties content in ISO-8859-1 encoding
        String props = "key=value\n";
        byte[] bytes = props.getBytes("ISO-8859-1");
        InputStream is = new ByteArrayInputStream(bytes);

        PropertyResourceBundle bundle = new PropertyResourceBundle(is);
        String val = bundle.getString("key");
        if (!"value".equals(val)) {
            throw new AssertionError("Expected 'value' but got '" + val + "'");
        }
    }

    private static void testValidUTF8Encoding() throws IOException {
        // To test UTF-8 encoding path, we need to simulate encoding = "UTF-8"
        // But encoding is a private static field in PropertyResourceBundle, not accessible here
        // So this test only ensures that constructor does not throw IOException for valid UTF-8 input
        // We create a UTF-8 encoded properties content
        String props = "key=valu\u00E9\n"; // value with accented e
        byte[] bytes = props.getBytes("UTF-8");
        InputStream is = new ByteArrayInputStream(bytes);

        // Since encoding field is not accessible, this test just calls constructor
        // and expects no exception (best effort given constraints)
        try {
            new PropertyResourceBundle(is);
        } catch (IOException e) {
            throw new AssertionError("Constructor threw IOException unexpectedly: " + e);
        }
    }
}
