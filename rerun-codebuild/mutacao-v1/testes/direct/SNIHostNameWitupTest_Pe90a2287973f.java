/* @test    @summary Test createSNIMatcher with valid and null regex    @run main SNIHostNameWitupTest_Pe90a2287973f */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_Pe90a2287973f {

    public static void main(String[] args) {
        testCreateSNIMatcherValid();
        testCreateSNIMatcherNull();
    }

    private static void testCreateSNIMatcherValid() {
        String regex = "^test.*";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        if (matcher == null) {
            throw new AssertionError("Expected non-null SNIMatcher for valid regex");
        }
        if (!matcher.getClass().getName().contains("SNIHostNameMatcher")) {
            throw new AssertionError("Expected SNIHostNameMatcher class");
        }
    }

    private static void testCreateSNIMatcherNull() {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
