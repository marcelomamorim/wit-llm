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
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension(null, null);

        // Test get(KEY_ID) returns null when id is null
        Object keyId = ext.get(AuthorityKeyIdentifierExtension.KEY_ID);
        if (keyId != null) {
            throw new RuntimeException("Expected null for KEY_ID when id is null");
        }

        // Test get(AUTH_NAME) returns null when names is null
        Object authName = ext.get(AuthorityKeyIdentifierExtension.AUTH_NAME);
        if (authName != null) {
            throw new RuntimeException("Expected null for AUTH_NAME when names is null");
        }

        // Test get(SERIAL_NUMBER) returns null when serialNum is null
        Object serialNum = ext.get(AuthorityKeyIdentifierExtension.SERIAL_NUMBER);
        if (serialNum != null) {
            throw new RuntimeException("Expected null for SERIAL_NUMBER when serialNum is null");
        }

        // Test get with unrecognized name throws IOException
        boolean threw = false;
        try {
            ext.get("unknown_attribute_name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }

        // Test get with case-insensitive keys
        // Since id, names, serialNum are null, expect null returns
        if (ext.get("KEY_ID") != null) {
            throw new RuntimeException("Expected null for KEY_ID uppercase");
        }
        if (ext.get("auth_name") != null) {
            throw new RuntimeException("Expected null for auth_name lowercase");
        }
        if (ext.get("SeRiAl_NuMbEr") != null) {
            throw new RuntimeException("Expected null for SERIAL_NUMBER mixed case");
        }

        // Test with non-null fields
        KeyIdentifier kid = new KeyIdentifier(new byte[] {1, 2, 3});
        GeneralNames gnames = new GeneralNames();
        SerialNumber sn = new SerialNumber(12345);
        AuthorityKeyIdentifierExtension ext2 = new AuthorityKeyIdentifierExtension(kid, gnames, sn);

        if (ext2.get(AuthorityKeyIdentifierExtension.KEY_ID) != kid) {
            throw new RuntimeException("Expected KeyIdentifier instance returned");
        }
        if (ext2.get(AuthorityKeyIdentifierExtension.AUTH_NAME) != gnames) {
            throw new RuntimeException("Expected GeneralNames instance returned");
        }
        if (ext2.get(AuthorityKeyIdentifierExtension.SERIAL_NUMBER) != sn) {
            throw new RuntimeException("Expected SerialNumber instance returned");
        }

        System.out.println("All tests passed.");
    }
}
