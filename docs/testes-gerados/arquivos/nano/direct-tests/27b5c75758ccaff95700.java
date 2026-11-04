/* @test    @summary Test the copy constructor of Extension class for correctness.    @run main ExtensionWitupTest */

import sun.security.x509.Extension;
import sun.security.util.ObjectIdentifier;

public class ExtensionWitupTest {

    public static void main(String[] args) {
        testCopyConstructor();
    }

    static void testCopyConstructor() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            byte[] value = {0x01, 0x02, 0x03};
            boolean critical = true;
            Extension original = new Extension(oid, critical, value);
            Extension copy = new Extension(original);
            assert copy.extensionId.equals(original.extensionId) : "extensionId mismatch";
            assert copy.critical == original.critical : "critical flag mismatch";
            assert java.util.Arrays.equals(copy.extensionValue, original.extensionValue) : "extensionValue mismatch";
        } catch (Exception e) {
            throw new RuntimeException("Test failed with exception: " + e, e);
        }
    }
}
