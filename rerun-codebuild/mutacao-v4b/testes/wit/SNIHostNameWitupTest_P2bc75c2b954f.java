/* @test
   @summary Tests createSNIMatcher method for null and valid inputs.
   @run main SNIHostNameWitupTest_P2bc75c2b954f
 */
import java.util.Objects;
public class SNIHostNameWitupTest_P2bc75c2b954f {
    public static void main(String[] args) {
        testCreateSNIMatcherNull();
        testCreateSNIMatcherValid();
    }

    private static void testCreateSNIMatcherNull() {
        try {
            javax.net.ssl.SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testCreateSNIMatcherValid() {
        String validRegex = "^.*$";
        javax.net.ssl.SNIMatcher matcher = javax.net.ssl.SNIHostName.createSNIMatcher(validRegex);
        if (matcher == null) {
            throw new AssertionError("Matcher should not be null for valid regex");
        }
    }
}
