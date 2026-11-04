/*
 * @test
 * @summary Test for PropertyResourceBundleCharset.newEncoder() method.
 * @run main PropertyResourceBundleCharsetWitupTest
 */

import sun.util.PropertyResourceBundleCharset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest {

    public static void main(String[] args) {
        testNewEncoderThrowsException();
    }

    public static void testNewEncoderThrowsException() {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            CharsetEncoder encoder = charset.newEncoder();
            // If no exception, test fails
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
