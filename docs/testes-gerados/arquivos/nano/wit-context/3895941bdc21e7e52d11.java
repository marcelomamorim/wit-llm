/* @test    @summary Test the constructor PropertyResourceBundle(InputStream) for PropertyResourceBundle class    @run main PropertyResourceBundleWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws IOException {
        testValidInputStream();
        testNullInputStream();
    }

    static void testValidInputStream() throws IOException {
        String propertiesContent = "key1=value1\nkey2=value2";
        InputStream stream = new ByteArrayInputStream(propertiesContent.getBytes("ISO-8859-1"));
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        if (!"value1".equals(bundle.getString("key1")) || !"value2".equals(bundle.getString("key2"))) {
            throw new AssertionError("Failed to read properties from InputStream");
        }
    }

    static void testNullInputStream() {
        try {
            new PropertyResourceBundle((InputStream) null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }
}
