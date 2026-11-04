/*
 * @test
 * @summary Verify PropertyResourceBundle.handleGetObject returns mapped values, null for absent keys and throws NullPointerException for a null key
 * @run main PropertyResourceBundleWitupTest
 */

import java.io.StringReader;

public class PropertyResourceBundleWitupTest {
    public static void main(String[] args) throws Exception {
        PropertyResourceBundle prb = new PropertyResourceBundle(new StringReader("key1=value1\n"));

        Object value = prb.handleGetObject("key1");
        if (!"value1".equals(value)) {
            throw new RuntimeException("Expected value1 but got " + value);
        }

        if (prb.handleGetObject("absent") != null) {
            throw new RuntimeException("Expected null for absent key");
        }

        boolean thrown = false;
        try {
            prb.handleGetObject(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null key");
        }
    }
}
