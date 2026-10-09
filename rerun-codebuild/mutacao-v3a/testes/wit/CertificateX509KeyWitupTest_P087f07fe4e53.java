/* @test    @summary Test for CertificateX509Key.set(String, Object) handling unrecognized attribute names    @run main CertificateX509KeyWitupTest_P087f07fe4e53 */

import sun.security.x509.CertificateX509Key;
import java.io.IOException;
import java.security.PublicKey;

public class CertificateX509KeyWitupTest_P087f07fe4e53 {

    public static void main(String[] args) throws Exception {
        testSetWithRecognizedName();
        testSetWithUnrecognizedName();
    }

    public static void testSetWithRecognizedName() throws Exception {
        // Assuming a valid PublicKey object or mocking it is complex;
        // we will pass null and focus on behavior for recognized name.
        CertificateX509Key certKey = new CertificateX509Key();
        certKey.set("value", null); // Recognized attribute; should set without exception.
    }

    public static void testSetWithUnrecognizedName() {
        CertificateX509Key certKey = new CertificateX509Key();
        try {
            certKey.set("unknown", new Object());
            // Expecting IOException due to unrecognized attribute name
            throw new AssertionError("Expected IOException for unknown attribute name.");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
