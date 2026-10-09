/* @test    @summary Test sun.security.x509.CertificateX509Key.get(String) method    @run main CertificateX509KeyWitupTest_P7536ca33fa06 */

import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;
import java.io.IOException;
import java.util.Base64;

public class CertificateX509KeyWitupTest_P7536ca33fa06 {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidKey();
    }

    private static void testGetWithValidKey() throws Exception {
        // Create a dummy PublicKey object for testing
        PublicKey dummyKey = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        PublicKey result = certKey.get("value");
        if (result != dummyKey) {
            throw new AssertionError("Expected the same PublicKey instance to be returned.");
        }
        System.out.println("testGetWithValidKey passed.");
    }

    private static void testGetWithInvalidKey() throws Exception {
        PublicKey dummyKey = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        try {
            certKey.get("invalid_name");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            // Expected exception
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message.");
            }
        }
        System.out.println("testGetWithInvalidKey passed.");
    }

    // Dummy PublicKey implementation for testing purposes
    static class DummyPublicKey implements PublicKey {
        @Override
        public String getAlgorithm() { return "RSA"; }
        @Override
        public String getFormat() { return "X.509"; }
        @Override
        public byte[] getEncoded() { return Base64.getDecoder().decode(
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtVh/3H+pi9OQp0W3w6kY" +
            "e8Kx6qVAnp4k3Y3T4WwU1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ1zF1k5nF" +
            "Q1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ1zF1k5nFQ" ); }
    }
}
