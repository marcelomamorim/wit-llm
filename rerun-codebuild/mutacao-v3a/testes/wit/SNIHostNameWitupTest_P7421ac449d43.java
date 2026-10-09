/* @test    @summary Test createSNIMatcher with various inputs    @run main SNIHostNameWitupTest_P7421ac449d43 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_P7421ac449d43 {

    public static void main(String[] args) {
        testCreateSNIMatcherNull();
        testCreateSNIMatcherInvalidPattern();
    }

    private static void testCreateSNIMatcherNull() {
        try {
            SNIHostName.createSNIMatcher(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testCreateSNIMatcherInvalidPattern() {
        String invalidPattern = "[abc"; // invalid regex
        try {
            SNIHostName.createSNIMatcher(invalidPattern);
            System.err.println("Expected IllegalArgumentException not thrown");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
