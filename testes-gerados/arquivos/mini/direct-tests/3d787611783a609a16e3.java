/*
 * @test
 * @summary Test CertificateX509Key.delete(String) method behavior
 * @run main CertificateX509KeyWitupTest
 */

import sun.security.x509.CertificateX509Key;
import java.io.IOException;

public class CertificateX509KeyWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidKeyName();
        testDeleteWithInvalidNameThrows();
    }

    private static void testDeleteWithValidKeyName() throws IOException {
        // Create instance with dummy PublicKey implementation
        CertificateX509Key certKey = new CertificateX509Key(new DummyPublicKey());
        // Delete with valid key name
        certKey.delete("value");
        // After delete, toString should return empty string (key == null)
        if (!"".equals(certKey.toString())) {
            throw new AssertionError("Expected empty string after delete with valid key name");
        }
    }

    private static void testDeleteWithInvalidNameThrows() {
        CertificateX509Key certKey = new CertificateX509Key(new DummyPublicKey());
        try {
            certKey.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }

    // Minimal dummy PublicKey implementation for testing
    private static class DummyPublicKey implements java.security.PublicKey {
        private static final long serialVersionUID = 1L;

        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[0]; }
        public String toString() { return "DummyPublicKey"; }
    }
}
