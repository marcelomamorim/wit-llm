/*
 * @test
 * @summary Test AuthorityKeyIdentifierExtension.encode(OutputStream) behavior
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.GeneralNames;
import sun.security.x509.SerialNumber;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() throws IOException {
        // Create an instance with all null fields to force encodeThis to set extensionValue
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue was null");
        }
    }

    private static void testEncodeWithNonNullExtensionValue() throws IOException {
        // Create KeyIdentifier with a sample byte array
        byte[] keyIdBytes = new byte[] {1, 2, 3, 4, 5};
        KeyIdentifier kid = new KeyIdentifier(keyIdBytes);

        // Create AuthorityKeyIdentifierExtension with kid, null names and serialNum
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, null, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] encoded = baos.toByteArray();

        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoded output is empty when extensionValue was set");
        }
    }
}
