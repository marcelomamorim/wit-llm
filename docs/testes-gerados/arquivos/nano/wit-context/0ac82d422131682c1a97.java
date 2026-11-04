/* @test    @summary Test for PropertyResourceBundle constructor with Reader input.    @run main PropertyResourceBundleWitupTest */

import java.io.StringReader;
import java.io.IOException;
import java.util.PropertyResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) throws IOException {
        testValidReader();
        testNullReader();
    }

    static void testValidReader() {
        String propertiesContent = "key1=value1\nkey2=value2";
        StringReader reader = new StringReader(propertiesContent);
        try {
            PropertyResourceBundle bundle = new PropertyResourceBundle(reader);
            String value1 = bundle.getString("key1");
            String value2 = bundle.getString("key2");
            if (!"value1".equals(value1) || !"value2".equals(value2)) {
                throw new AssertionError("Unexpected property values: " + value1 + ", " + value2);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    static void testNullReader() {
        try {
            new PropertyResourceBundle((Reader) null);
            throw new AssertionError("Expected NullPointerException for null Reader");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
