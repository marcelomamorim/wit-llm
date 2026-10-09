/* @test
   @summary Verifies that calling newEncoder() on PropertyResourceBundleCharset throws UnsupportedOperationException.
   @run main PropertyResourceBundleCharsetWitupTest_P369989ccd1c1
 */
import java.nio.charset.CharsetEncoder;
import java.util.Objects;

public class PropertyResourceBundleCharsetWitupTest_P369989ccd1c1 {
    public static void main(String[] args) {
        sun.util.PropertyResourceBundleCharset charset = new sun.util.PropertyResourceBundleCharset(false);
        try {
            charset.newEncoder();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
