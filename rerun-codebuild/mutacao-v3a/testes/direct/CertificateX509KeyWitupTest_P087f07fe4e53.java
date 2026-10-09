/* @test    @summary Test the set method of CertificateX509Key for valid and invalid attribute names.    @run main CertificateX509KeyWitupTest_P087f07fe4e53 */

import java.io.IOException;
import java.security.PublicKey;

public class CertificateX509KeyWitupTest_P087f07fe4e53 {

    public static void main(String[] args) throws Exception {
        testSetWithValidKey();
        testSetWithInvalidName();
    }

    static void testSetWithValidKey() throws Exception {
        // Mock a minimal PublicKey implementation
        PublicKey dummyKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }

            @Override
            public String getFormat() { return "X.509"; }

            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        sun.security.x509.CertificateX509Key certKey = new sun.security.x509.CertificateX509Key(dummyKey);
        // Set a new key (should succeed)
        certKey.set("value", dummyKey);
        // Verify that get returns the same key
        PublicKey retrievedKey = certKey.get("value");
        assert retrievedKey == dummyKey : "The retrieved key should be the same as the set key";
    }

    static void testSetWithInvalidName() {
        // Mock a minimal PublicKey implementation
        PublicKey dummyKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }

            @Override
            public String getFormat() { return "X.509"; }

            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        sun.security.x509.CertificateX509Key certKey;
        try {
            certKey = new sun.security.x509.CertificateX509Key(dummyKey);
            // Attempt to set with an invalid attribute name
            certKey.set("invalid", dummyKey);
            assert false : "Expected IOException for invalid attribute name";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Exception message should indicate unrecognized attribute";
        }
    }
}
