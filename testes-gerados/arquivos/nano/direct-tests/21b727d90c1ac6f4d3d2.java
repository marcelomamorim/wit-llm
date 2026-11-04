/* @test    @summary Test OCSPNoCheckExtension.set method throws IOException for any attribute name    @run main OCSPNoCheckExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) {
        testSetThrowsIOException();
    }

    public static void testSetThrowsIOException() {
        OCSPNoCheckExtension ext = new OCSPNoCheckExtension();
        boolean exceptionThrown = false;
        try {
            ext.set("anyAttribute", new Object());
        } catch (IOException e) {
            exceptionThrown = true;
            if (!"No attribute is allowed by CertAttrSet:OCSPNoCheckExtension.".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException was not thrown.");
        }
    }
}
