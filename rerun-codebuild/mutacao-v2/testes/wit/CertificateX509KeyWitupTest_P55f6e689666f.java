/*
 * @test
 * @summary Test sun.security.x509.CertificateX509Key.get(String) method behavior
 * @run main CertificateX509KeyWitupTest_P55f6e689666f
 */

import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;
import java.io.IOException;

public class CertificateX509KeyWitupTest_P55f6e689666f {

    static class DummyPublicKey implements PublicKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[] {1,2,3}; }
        public String toString() { return "DummyPublicKey"; }
    }

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidName();
    }

    private static void testGetWithValidKey() throws IOException {
        PublicKey key = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(key);
        PublicKey result = certKey.get("value");
        if (result == null) {
            throw new RuntimeException("get returned null for valid key name");
        }
        if (!result.equals(key)) {
            throw new RuntimeException("get returned unexpected key instance");
        }
    }

    private static void testGetWithInvalidName() {
        PublicKey key = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(key);
        try {
            certKey.get("invalidName");
            throw new RuntimeException("get did not throw IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
