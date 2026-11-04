/* @test    @summary Test PropertyResourceBundle constructor with InputStream    @run main PropertyResourceBundleWitupTest */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws IOException {
        testValidInputStream();
        testEmptyInputStream();
        testInvalidEncoding();
    }

    static void testValidInputStream() throws IOException {
        String propertiesContent = "key1=value1\nkey2=value2";
        InputStream stream = new ByteArrayInputStream(propertiesContent.getBytes("ISO-8859-1"));
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        assert "value1".equals(bundle.getString("key1")) : "Expected key1 to be 'value1'";
        assert "value2".equals(bundle.getString("key2")) : "Expected key2 to be 'value2'";
    }

    static void testEmptyInputStream() throws IOException {
        String propertiesContent = "";
        InputStream stream = new ByteArrayInputStream(propertiesContent.getBytes("ISO-8859-1"));
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        assert bundle.getKeys().hasMoreElements() == false : "Expected no keys in empty bundle";
    }

    static void testInvalidEncoding() {
        String invalidBytes = new String(new byte[]{(byte)0xC3, (byte)0x28});
        InputStream stream = new ByteArrayInputStream(invalidBytes.getBytes());
        try {
            new PropertyResourceBundle(stream);
            assert false : "Expected IOException due to invalid encoding";
        } catch (IOException e) {
            // Expected exception
        }
    }
}
