/* @test    @summary Test for CertificateX509Key.set method to verify correct behavior and exception handling.    @run main CertificateX509KeyWitupTest */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidKey();
        testSetWithInvalidName();
    }

    private static void testSetWithValidKey() throws Exception {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        PublicKey newKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "DSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {4, 5, 6}; }
        };
        certKey.set("value", newKey);
        // Verify that the key was set correctly
        if (certKey.get("value") != newKey) {
            throw new AssertionError("Key was not set correctly.");
        }
        System.out.println("testSetWithValidKey passed.");
    }

    private static void testSetWithInvalidName() {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        try {
            certKey.set("invalidName", new Object());
            throw new AssertionError("Expected IOException was not thrown.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testSetWithInvalidName passed.");
        }
    }
}
