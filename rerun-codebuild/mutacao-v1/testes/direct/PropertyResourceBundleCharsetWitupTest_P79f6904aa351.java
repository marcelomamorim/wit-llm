/* @test    @summary Test that PropertyResourceBundleCharset.newEncoder() throws UnsupportedOperationException    @run main PropertyResourceBundleCharsetWitupTest_P79f6904aa351 */

import sun.util.PropertyResourceBundleCharset;

public class PropertyResourceBundleCharsetWitupTest_P79f6904aa351 {

    public static void main(String[] args) {
        PropertyResourceBundleCharset charset = new PropertyResourceBundleCharset(false);
        try {
            charset.newEncoder();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
