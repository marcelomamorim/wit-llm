/* @test    @summary Test for PropertyResourceBundleCharset.newEncoder() method   @run main PropertyResourceBundleCharsetWitupTest_Pfa06e5c852db */

import sun.util.PropertyResourceBundleCharset;
import java.nio.charset.CharsetEncoder;

public class PropertyResourceBundleCharsetWitupTest_Pfa06e5c852db {

    public static void main(String[] args) {
        testNewEncoderThrowsException();
    }

    public static void testNewEncoderThrowsException() {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            CharsetEncoder encoder = charset.newEncoder();
            // Fail the test if no exception is thrown
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
