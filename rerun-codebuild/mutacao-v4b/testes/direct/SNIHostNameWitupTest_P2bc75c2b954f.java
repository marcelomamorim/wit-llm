/* @test
   @summary Tests the createSNIMatcher method of SNIHostName.
   @run main SNIHostNameWitupTest_P2bc75c2b954f
 */
import java.util.Objects;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_P2bc75c2b954f {
    public static void main(String[] args) {
        testCreateSNIMatcherWithNull();
        testCreateSNIMatcherWithValidRegex();
    }

    private static void testCreateSNIMatcherWithNull() {
        try {
            SNIMatcher matcher = SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testCreateSNIMatcherWithValidRegex() {
        String regex = "^test.*$";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        Objects.requireNonNull(matcher, "Matcher should not be null");
        // Additional behavior tests could be added if SNIMatcher exposes methods
    }
}
