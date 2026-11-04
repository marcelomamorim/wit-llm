/* @test    @summary Test AuthorityKeyIdentifierExtension.encode method for null extensionValue    @run main AuthorityKeyIdentifierExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithEmptyExtensionValue();
    }

    static void testEncodeWithNullExtensionValue() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // forcibly set extensionValue to null to simulate uninitialized state
        ext.extensionValue = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        // Expect the encode method to handle null extensionValue gracefully
        if (result.length == 0) {
            System.out.println("PASS: encode with null extensionValue produces empty output.");
        } else {
            System.out.println("FAIL: encode with null extensionValue produced non-empty output.");
        }
    }

    static void testEncodeWithEmptyExtensionValue() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // simulate empty extension value
        ext.extensionValue = new byte[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        // Expect the encode method to handle empty extensionValue gracefully
        if (result.length == 0) {
            System.out.println("PASS: encode with empty extensionValue produces empty output.");
        } else {
            System.out.println("FAIL: encode with empty extensionValue produced non-empty output.");
        }
    }
}
