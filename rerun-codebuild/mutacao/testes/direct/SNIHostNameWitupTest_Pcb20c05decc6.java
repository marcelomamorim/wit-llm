/* @test    @summary Test createSNIMatcher with valid and null regex    @run main SNIHostNameWitupTest_Pcb20c05decc6 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest_Pcb20c05decc6 {

    public static void main(String[] args) {
        testCreateSNIMatcherWithValidRegex();
        testCreateSNIMatcherWithNull();
    }

    private static void testCreateSNIMatcherWithValidRegex() {
        String regex = "^test.*";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        if (matcher == null) {
            throw new AssertionError("Expected non-null SNIMatcher for valid regex");
        }
    }

    private static void testCreateSNIMatcherWithNull() {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new AssertionError("Expected NullPointerException for null regex");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
