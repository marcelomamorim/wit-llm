/* @test    @summary Test for sun.security.x509.PolicyMappingsExtension.encode(OutputStream out) method.
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithValidExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.extensionValue = null; // forcibly set to null to test encode logic
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result.length == 0) {
            System.out.println("PASS: encode with null extensionValue produces empty output.");
        } else {
            System.out.println("FAIL: encode with null extensionValue produces non-empty output.");
        }
    }

    private static void testEncodeWithValidExtensionValue() throws IOException {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        // Simulate a non-null extensionValue by calling encodeThis with a dummy map list
        ext.maps = java.util.Collections.emptyList();
        ext.encodeThis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result != null && result.length > 0) {
            System.out.println("PASS: encode with valid extensionValue produces output.");
        } else {
            System.out.println("FAIL: encode with valid extensionValue produces empty output.");
        }
    }
}
