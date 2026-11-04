/* @test    @summary Test InhibitAnyPolicyExtension encode method for JDK 11+28    @run main InhibitAnyPolicyExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithDefault();
        testEncodeWithSpecificValue();
        System.out.println("All tests passed.");
    }

    private static void testEncodeWithDefault() throws IOException {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with default value failed: output is null or empty");
        }
    }

    private static void testEncodeWithSpecificValue() throws IOException {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding with specific value failed: output is null or empty");
        }
    }
}
