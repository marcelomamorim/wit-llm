/*
 * @test
 * @summary Test for CertificateX509Key.get(String)
 * @run main CertificateX509KeyWitupTest_P7536ca33fa06
 */

import sun.security.x509.CertificateX509Key;
import java.io.IOException;
import java.security.PublicKey;
import java.util.Arrays;

public class CertificateX509KeyWitupTest_P7536ca33fa06 {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidKey();
    }

    static void testGetWithValidKey() throws Exception {
        // Mock a PublicKey object for testing purposes
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[]{1,2,3}; }
            @Override
            public String toString() { return "MockPublicKey"; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        // Test get with key name "value" (case-insensitive)
        PublicKey result = certKey.get("value");
        if (result != mockKey) {
            throw new AssertionError("Expected the same mockKey instance.");
        }
        // Test get with key name "VALUE" (case-insensitive)
        result = certKey.get("VALUE");
        if (result != mockKey) {
            throw new AssertionError("Expected the same mockKey instance.");
        }
        System.out.println("testGetWithValidKey passed.");
    }

    static void testGetWithInvalidKey() {
        try {
            // Use a dummy PublicKey for testing
            PublicKey dummyKey = new PublicKey() {
                @Override
                public String getAlgorithm() { return "RSA"; }
                @Override
                public String getFormat() { return "X.509"; }
                @Override
                public byte[] getEncoded() { return new byte[]{1,2,3}; }
                @Override
                public String toString() { return "Dummy"; }
            };
            CertificateX509Key certKey = new CertificateX509Key(dummyKey);
            // Use an invalid attribute name to trigger exception
            certKey.get("invalid");
            throw new AssertionError("Expected IOException was not thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
            System.out.println("testGetWithInvalidKey passed.");
        }
    }
}
