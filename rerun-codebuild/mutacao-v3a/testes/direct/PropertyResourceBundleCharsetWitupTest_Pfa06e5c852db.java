/*
 * @test
 * @summary Test sun.util.PropertyResourceBundleCharset newEncoder method.
 * @run main PropertyResourceBundleCharsetWitupTest_Pfa06e5c852db
 */

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest_Pfa06e5c852db {

    public static void main(String[] args) {
        testNewEncoderThrows();
    }

    public static void testNewEncoderThrows() {
        try {
            // Instantiate the class with any boolean, e.g., false
            sun.util.PropertyResourceBundleCharset charset = 
                new sun.util.PropertyResourceBundleCharset(false);
            // Call newEncoder(), expecting an UnsupportedOperationException
            CharsetEncoder encoder = charset.newEncoder();
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            // Optionally check the message
            if (!"Encoding is not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        } catch (Exception e) {
            // Unexpected exception, fail test
            throw new AssertionError("Unexpected exception: ", e);
        }
    }
}
