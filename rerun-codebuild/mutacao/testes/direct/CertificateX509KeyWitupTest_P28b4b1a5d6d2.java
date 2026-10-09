/* @test    @summary Test for CertificateX509Key.get(String) method    @run main CertificateX509KeyWitupTest_P28b4b1a5d6d2 */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_P28b4b1a5d6d2 {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidName();
    }

    private static void testGetWithValidKey() throws Exception {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        PublicKey result = certKey.get("value");
        if (result != mockKey) {
            throw new AssertionError("Expected the same PublicKey instance");
        }
    }

    private static void testGetWithInvalidName() {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        try {
            CertificateX509Key certKey = new CertificateX509Key(mockKey);
            certKey.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
