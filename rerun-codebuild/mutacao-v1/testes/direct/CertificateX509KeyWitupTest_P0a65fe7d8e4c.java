/* @test    @summary Test the set method of CertificateX509Key for valid and invalid attribute names    @run main CertificateX509KeyWitupTest_P0a65fe7d8e4c */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_P0a65fe7d8e4c {

    public static void main(String[] args) throws Exception {
        testSetValidKey();
        testSetInvalidAttribute();
    }

    private static void testSetValidKey() throws Exception {
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        certKey.set("value", mockKey);
        if (certKey.toString() == null || certKey.toString().isEmpty()) {
            throw new AssertionError("FAIL: toString() should not be empty after set.");
        }
    }

    private static void testSetInvalidAttribute() {
        PublicKey dummyKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        try {
            certKey.set("invalid", dummyKey);
            throw new AssertionError("FAIL: Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("FAIL: Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
