/* @test    @summary Verify createSNIMatcher with valid and null regex    @run main SNIHostNameWitupTest_P7421ac449d43 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_P7421ac449d43 {

    public static void main(String[] args) {
        testCreateSNIMatcherWithValidRegex();
        testCreateSNIMatcherWithNull();
    }

    static void testCreateSNIMatcherWithValidRegex() {
        String regex = "^test.*";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        if (matcher == null) {
            throw new AssertionError("Expected non-null SNIMatcher for valid regex");
        }
    }

    static void testCreateSNIMatcherWithNull() {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
