/*
 * @test
 * @summary Test that PropertyResourceBundleCharset.newEncoder() throws UnsupportedOperationException
 * @run main PropertyResourceBundleCharsetWitupTest
 */

import sun.util.PropertyResourceBundleCharset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest {
    public static void main(String[] args) {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            CharsetEncoder encoder = charset.newEncoder();
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
