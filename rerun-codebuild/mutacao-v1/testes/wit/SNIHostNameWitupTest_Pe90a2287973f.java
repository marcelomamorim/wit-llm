/* @test    @summary Test createSNIMatcher with null input expecting NullPointerException    @run main SNIHostNameWitupTest_Pe90a2287973f */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_Pe90a2287973f {

    public static void main(String[] args) {
        testCreateSNIMatcherNull();
        testCreateSNIMatcherInvalidPattern();
    }

    private static void testCreateSNIMatcherNull() {
        try {
            SNIMatcher matcher = SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"The regular expression cannot be null".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testCreateSNIMatcherInvalidPattern() {
        String invalidPattern = "["; // invalid regex
        try {
            SNIMatcher matcher = SNIHostName.createSNIMatcher(invalidPattern);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e.getClass().getName());
        }
    }
}
