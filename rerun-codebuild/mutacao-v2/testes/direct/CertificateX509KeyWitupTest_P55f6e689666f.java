/*
 * @test
 * @summary Test CertificateX509Key.get(String) method behavior
 * @run main CertificateX509KeyWitupTest_P55f6e689666f
 */

import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;
import java.io.IOException;

public class CertificateX509KeyWitupTest_P55f6e689666f {
    public static void main(String[] args) throws Exception {
        testGetWithValidKeyName();
        testGetWithInvalidNameThrows();
    }

    private static void testGetWithValidKeyName() throws IOException {
        PublicKey dummyKey = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        PublicKey result = certKey.get("value");
        if (result == null) {
            throw new RuntimeException("Expected non-null PublicKey from get with 'value'");
        }
        if (result != dummyKey) {
            throw new RuntimeException("Returned PublicKey is not the same instance as set");
        }
        // Also test case-insensitivity
        PublicKey result2 = certKey.get("VaLuE");
        if (result2 != dummyKey) {
            throw new RuntimeException("Returned PublicKey is not case-insensitive for 'value'");
        }
    }

    private static void testGetWithInvalidNameThrows() {
        PublicKey dummyKey = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        try {
            certKey.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }

    // Minimal dummy PublicKey implementation for testing
    private static class DummyPublicKey implements PublicKey {
        private static final long serialVersionUID = 1L;
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[0]; }
        public String toString() { return "DummyPublicKey"; }
    }
}
