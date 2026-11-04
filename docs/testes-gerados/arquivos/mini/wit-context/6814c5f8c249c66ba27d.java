/*
 * @test
 * @summary Test sun.security.x509.AuthorityKeyIdentifierExtension.get(String) method
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import sun.security.x509.AuthorityKeyIdentifierExtension;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.GeneralNames;
import sun.security.x509.SerialNumber;
import java.io.IOException;

public class AuthorityKeyIdentifierExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetKnownAttributes();
        testGetUnknownAttributeThrows();
    }

    private static void testGetKnownAttributes() throws IOException {
        // Prepare dummy components
        byte[] keyIdBytes = new byte[] {1, 2, 3};
        KeyIdentifier kid = new KeyIdentifier(keyIdBytes);
        GeneralNames names = new GeneralNames();
        SerialNumber sn = new SerialNumber(new java.math.BigInteger("123456789"));

        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(kid, names, sn);

        // Test get(KEY_ID) returns the same KeyIdentifier instance
        Object gotKid = ext.get(AuthorityKeyIdentifierExtension.KEY_ID);
        if (!(gotKid instanceof KeyIdentifier)) {
            throw new RuntimeException("Expected KeyIdentifier instance for KEY_ID");
        }
        if (gotKid != kid) {
            throw new RuntimeException("Returned KeyIdentifier instance differs from original");
        }

        // Test get(AUTH_NAME) returns the same GeneralNames instance
        Object gotNames = ext.get(AuthorityKeyIdentifierExtension.AUTH_NAME);
        if (!(gotNames instanceof GeneralNames)) {
            throw new RuntimeException("Expected GeneralNames instance for AUTH_NAME");
        }
        if (gotNames != names) {
            throw new RuntimeException("Returned GeneralNames instance differs from original");
        }

        // Test get(SERIAL_NUMBER) returns the same SerialNumber instance
        Object gotSn = ext.get(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);
        if (!(gotSn instanceof SerialNumber)) {
            throw new RuntimeException("Expected SerialNumber instance for SERIAL_NUMBER");
        }
        if (gotSn != sn) {
            throw new RuntimeException("Returned SerialNumber instance differs from original");
        }
    }

    private static void testGetUnknownAttributeThrows() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(Boolean.FALSE, null);
        try {
            ext.get("unknown_attribute");
            throw new RuntimeException("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
