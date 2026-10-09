/*
 * @test
 * @summary Test PropertyResourceBundleCharset.newEncoder() throws UnsupportedOperationException
 * @run main PropertyResourceBundleCharsetWitupTest_P9da912cf12fc
 */

import sun.util.PropertyResourceBundleCharset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest_P9da912cf12fc {
    public static void main(String[] args) {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            CharsetEncoder encoder = charset.newEncoder();
            throw new Error("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            if (!"Encoding is not supported".equals(e.getMessage())) {
                throw new Error("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
