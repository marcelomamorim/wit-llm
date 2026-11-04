/* @test    @summary Test deserialization constructor of sun.security.x509.Extension for copying fields.    @run main sun.security.x509.ExtensionWitupTest */

import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;
import java.io.IOException;

public class ExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        // Prepare a dummy Extension object
        ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
        boolean critical = true;
        byte[] value = {0x01, 0x02, 0x03};
        Extension extOriginal = new Extension(oid, critical, value);
        // Use copy constructor
        Extension extCopy = new Extension(extOriginal);
        // Validate fields are copied correctly
        if (extCopy.extensionId == null || !extCopy.extensionId.equals(oid)) {
            throw new AssertionError("extensionId mismatch");
        }
        if (extCopy.critical != critical) {
            throw new AssertionError("critical flag mismatch");
        }
        if (extCopy.extensionValue == null || !java.util.Arrays.equals(extCopy.extensionValue, value)) {
            throw new AssertionError("extensionValue mismatch");
        }
        System.out.println("Extension copy constructor test passed.");
    }
}
