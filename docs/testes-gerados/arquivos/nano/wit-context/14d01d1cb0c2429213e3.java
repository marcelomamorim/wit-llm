/* @test    @summary Test for AuthorityInfoAccessExtension.encode method handling null extensionValue */
/* @run main AuthorityInfoAccessExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.AuthorityInfoAccessExtension;

public class AuthorityInfoAccessExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(
                java.util.Collections.emptyList());
            ext.extensionValue = null; // forcibly set to null to simulate uninitialized state
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with null extensionValue produces output.");
            } else {
                System.out.println("FAIL: encode with null extensionValue produces empty output.");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with null extensionValue threw IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: encode with null extensionValue threw unexpected exception: " + e);
        }
    }

    private static void testEncodeWithNonNullExtensionValue() {
        try {
            AuthorityInfoAccessExtension ext = new AuthorityInfoAccessExtension(
                java.util.Collections.emptyList());
            // Simulate extensionValue being set to a dummy byte array
            ext.extensionValue = new byte[] {1, 2, 3};
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with non-null extensionValue produces output.");
            } else {
                System.out.println("FAIL: encode with non-null extensionValue produces empty output.");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with non-null extensionValue threw IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: encode with non-null extensionValue threw unexpected exception: " + e);
        }
    }
}
