/*
 * @test
 * @summary Test CertificateX509Key.set method behavior
 * @run main CertificateX509KeyWitupTest_Pd6707affc782
 */

import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;
import java.io.IOException;

public class CertificateX509KeyWitupTest_Pd6707affc782 {
    static class DummyPublicKey implements PublicKey {
        public String getAlgorithm() { return "Dummy"; }
        public String getFormat() { return "X.509"; }
        public byte[] getEncoded() { return new byte[0]; }
        public String toString() { return "DummyPublicKey"; }
    }

    public static void main(String[] args) throws Exception {
        CertificateX509Key certKey = new CertificateX509Key(new DummyPublicKey());

        // Test set with valid key name and valid PublicKey object
        DummyPublicKey newKey = new DummyPublicKey();
        certKey.set("value", newKey);
        PublicKey returnedKey = certKey.get("value");
        if (returnedKey != newKey) {
            throw new RuntimeException("set did not update the key correctly");
        }

        // Test set with valid key name in different case
        DummyPublicKey anotherKey = new DummyPublicKey();
        certKey.set("VaLuE", anotherKey);
        returnedKey = certKey.get("value");
        if (returnedKey != anotherKey) {
            throw new RuntimeException("set did not handle case-insensitive key name");
        }

        // Test set with invalid attribute name throws IOException
        boolean threw = false;
        try {
            certKey.set("invalidName", newKey);
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("set did not throw IOException for invalid attribute name");
        }
    }
}
