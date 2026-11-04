/* @test    @summary Test for CertificateX509Key.delete(String) method to verify exception path when attribute name is unrecognized    @run main CertificateX509KeyWitupTest */

import java.io.IOException;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Setup: create a dummy PublicKey object (null for simplicity, as actual key parsing is complex)
        PublicKey dummyKey = null;
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        // Call delete with recognized name ("value") - should not throw
        try {
            certKey.delete("value");
            System.out.println("testDeleteWithValidName passed");
        } catch (IOException e) {
            throw new AssertionError("Unexpected exception for valid name", e);
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        // Setup: create a dummy PublicKey object
        PublicKey dummyKey = null;
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        // Call delete with unrecognized name - should throw IOException
        boolean thrown = false;
        try {
            certKey.delete("invalidName");
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Exception message mismatch", e);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException was not thrown");
        }
        System.out.println("testDeleteWithInvalidName passed");
    }
}
