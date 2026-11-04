/*
 * @test
 * @summary Test CertificateX509Key.set(String,Object) method behavior
 * @run main CertificateX509KeyWitupTest
 */

import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;
import java.io.IOException;

public class CertificateX509KeyWitupTest {
    static class DummyPublicKey implements PublicKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[0]; }
        public String toString() { return "DummyPublicKey"; }
    }

    public static void main(String[] args) throws Exception {
        CertificateX509Key certKey = new CertificateX509Key(new DummyPublicKey());

        // Test set with valid name "value" and valid PublicKey object
        DummyPublicKey newKey = new DummyPublicKey();
        certKey.set("value", newKey);
        PublicKey returnedKey = certKey.get("value");
        if (returnedKey == null || !returnedKey.toString().equals("DummyPublicKey")) {
            throw new RuntimeException("set/get failed for valid key");
        }

        // Test set with valid name "VALUE" (case insensitive) and valid PublicKey object
        DummyPublicKey anotherKey = new DummyPublicKey();
        certKey.set("VALUE", anotherKey);
        PublicKey returnedKey2 = certKey.get("value");
        if (returnedKey2 == null || !returnedKey2.toString().equals("DummyPublicKey")) {
            throw new RuntimeException("set/get failed for case insensitive key");
        }

        // Test set with invalid name triggers IOException
        boolean threw = false;
        try {
            certKey.set("invalidName", new DummyPublicKey());
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        }
    }
}
