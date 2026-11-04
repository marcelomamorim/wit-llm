/* @test    @summary Test getEncodedKeyIdentifier method of AuthorityKeyIdentifierExtension class    @run main AuthorityKeyIdentifierExtensionWitupTest */

import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.util.DerOutputStream;
import java.io.IOException;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetEncodedKeyIdentifierWithId();
        testGetEncodedKeyIdentifierNull();
    }

    private static void testGetEncodedKeyIdentifierWithId() throws IOException {
        byte[] keyIdBytes = {0x01, 0x02, 0x03, 0x04};
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(
            new sun.security.util.KeyIdentifier(keyIdBytes), null, null);
        byte[] encoded = ext.getEncodedKeyIdentifier();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Expected non-null, non-empty byte array");
        }
        DerOutputStream derOut = new DerOutputStream();
        ext.getEncodedKeyIdentifier();
        derOut.write(encoded);
        System.out.println("testGetEncodedKeyIdentifierWithId passed.");
    }

    private static void testGetEncodedKeyIdentifierNull() throws IOException {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(
            (Boolean) null, null);
        byte[] result = ext.getEncodedKeyIdentifier();
        if (result != null) {
            throw new AssertionError("Expected null when id is null");
        }
        System.out.println("testGetEncodedKeyIdentifierNull passed.");
    }
}
