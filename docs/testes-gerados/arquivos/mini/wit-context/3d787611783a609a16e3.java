/*
 * @test
 * @summary Test sun.security.x509.CertificateX509Key.delete(String) method behavior
 * @run main CertificateX509KeyWitupTest
 */

import sun.security.x509.CertificateX509Key;
import java.io.IOException;
import java.security.PublicKey;

public class CertificateX509KeyWitupTest {

    static class DummyPublicKey implements PublicKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[0]; }
        public String toString() { return "DummyPublicKey"; }
    }

    public static void main(String[] args) throws Exception {
        testDeleteWithValidKeyName();
        testDeleteWithInvalidNameThrowsIOException();
    }

    private static void testDeleteWithValidKeyName() throws IOException {
        PublicKey key = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(key);
        certKey.delete("value"); // should set key to null without exception
        if (certKey.toString().length() != 0) {
            throw new RuntimeException("delete did not clear the key as expected");
        }
    }

    private static void testDeleteWithInvalidNameThrowsIOException() {
        PublicKey key = new DummyPublicKey();
        CertificateX509Key certKey = new CertificateX509Key(key);
        boolean threw = false;
        try {
            certKey.delete("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
