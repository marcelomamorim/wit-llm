/* @test    @summary Test for CertificateX509Key.set method handling attribute name validation    @run main CertificateX509KeyWitupTest_P0a65fe7d8e4c */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_P0a65fe7d8e4c {

    public static void main(String[] args) throws Exception {
        testSetWithValidKeyName();
        testSetWithInvalidName();
    }

    private static void testSetWithValidKeyName() throws Exception {
        PublicKey dummyKey = null; // Placeholder, as actual key creation is complex
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        try {
            certKey.set("value", dummyKey);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected exception for valid attribute name", e);
        }
    }

    private static void testSetWithInvalidName() {
        PublicKey dummyKey = null; // Placeholder
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        boolean thrown = false;
        try {
            certKey.set("invalidName", new Object());
        } catch (IOException e) {
            thrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message", e);
            }
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        }
    }
}
