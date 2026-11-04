/*
 * @test
 * @summary Verifies CertificateX509Key.set(String, Object) for the accepted attribute name, case-insensitive matching, null values, unknown names and wrong value types
 * @modules java.base/sun.security.x509
 * @run main CertificateX509KeyWitupTest
 */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    static final class TestKey implements PublicKey {
        private static final long serialVersionUID = 1L;
        private final String id;

        TestKey(String id) {
            this.id = id;
        }

        public String getAlgorithm() {
            return "TEST";
        }

        public String getFormat() {
            return "RAW";
        }

        public byte[] getEncoded() {
            return new byte[] { 1, 2, 3 };
        }

        public String toString() {
            return "TestKey(" + id + ")";
        }
    }

    public static void main(String[] args) throws Exception {
        TestKey k1 = new TestKey("k1");
        TestKey k2 = new TestKey("k2");

        CertificateX509Key c = new CertificateX509Key(k1);

        c.set("value", k2);
        if (c.get("value") != k2) {
            throw new RuntimeException("set(\"value\") did not update the key");
        }

        c.set("VALUE", k1);
        if (c.get("value") != k1) {
            throw new RuntimeException("set is not case-insensitive for upper case");
        }

        c.set("Value", null);
        if (c.get("value") != null) {
            throw new RuntimeException("set with null did not clear the key");
        }
        if (!c.toString().equals("")) {
            throw new RuntimeException("toString with null key should be empty");
        }

        c.set("value", k2);
        try {
            c.set("other", k1);
            throw new RuntimeException("expected IOException for unknown name");
        } catch (IOException e) {
            // expected
        }
        if (c.get("value") != k2) {
            throw new RuntimeException("failed set must not change the key");
        }

        try {
            c.set("value", "not a key");
            throw new RuntimeException("expected ClassCastException for non-PublicKey value");
        } catch (ClassCastException e) {
            // expected
        }
        if (c.get("value") != k2) {
            throw new RuntimeException("rejected value must not change the key");
        }
    }
}
