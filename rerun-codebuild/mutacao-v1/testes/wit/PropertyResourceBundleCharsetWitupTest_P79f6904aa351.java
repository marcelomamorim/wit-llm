/* @test    @summary Test for PropertyResourceBundleCharset.newEncoder() method to verify it throws UnsupportedOperationException.    @run main PropertyResourceBundleCharsetWitupTest_P79f6904aa351 */

import sun.util.PropertyResourceBundleCharset;

public class PropertyResourceBundleCharsetWitupTest_P79f6904aa351 {

    public static void main(String[] args) {
        testNewEncoderThrowsException();
    }

    public static void testNewEncoderThrowsException() {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            charset.newEncoder();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes if exception is caught
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
