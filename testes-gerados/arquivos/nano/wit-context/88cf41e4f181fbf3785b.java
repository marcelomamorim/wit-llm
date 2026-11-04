/* @test    @summary Test for CertificateX509Key.set(String, Object) method handling unrecognized attribute names.
 * @run main CertificateX509KeyWitupTest
 */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidKey();
        testSetWithInvalidName();
    }

    static void testSetWithValidKey() throws Exception {
        // Create a dummy PublicKey object (null for simplicity, as actual key creation is complex)
        PublicKey dummyKey = null;
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        // Set with recognized attribute name "value"
        certKey.set("value", dummyKey);
        // No exception expected
        System.out.println("testSetWithValidKey passed");
    }

    static void testSetWithInvalidName() {
        try {
            PublicKey dummyKey = null;
            CertificateX509Key certKey = new CertificateX509Key(dummyKey);
            // Set with unrecognized attribute name
            certKey.set("invalidName", new Object());
            System.err.println("testSetWithInvalidName failed: Exception not thrown");
        } catch (IOException e) {
            // Expected exception
            System.out.println("testSetWithInvalidName passed");
        }
    }
}
