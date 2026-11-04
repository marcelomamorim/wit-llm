/*
 * @test
 * @summary Verify PropertyResourceBundleCharset.newEncoder rejects encoding with UnsupportedOperationException
 * @modules java.base/sun.util
 * @run main PropertyResourceBundleCharsetWitupTest
 */
import sun.util.PropertyResourceBundleCharset;

public class PropertyResourceBundleCharsetWitupTest {
    public static void main(String[] args) {
        PropertyResourceBundleCharset cs = new PropertyResourceBundleCharset(false);
        try {
            cs.newEncoder();
        } catch (UnsupportedOperationException e) {
            if (!"Encoding is not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException");
    }
}
