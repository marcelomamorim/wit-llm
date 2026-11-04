/*
 * @test
 * @summary Test copy constructor of sun.security.x509.Extension
 * @run main ExtensionWitupTest
 */

import sun.security.x509.Extension;
import sun.security.x509.ObjectIdentifier;

public class ExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create original Extension
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        boolean critical = true;
        byte[] value = new byte[] {1, 2, 3, 4, 5};
        Extension original = new Extension(oid, critical, value);

        // Copy using copy constructor
        Extension copy = new Extension(original);

        // Check that fields are copied correctly
        if (!copy.extensionId.equals(original.extensionId)) {
            throw new RuntimeException("extensionId not copied correctly");
        }
        if (copy.critical != original.critical) {
            throw new RuntimeException("critical flag not copied correctly");
        }
        if (copy.extensionValue != original.extensionValue) {
            throw new RuntimeException("extensionValue reference not copied correctly");
        }

        // Check that modifying original's extensionValue affects copy (shallow copy)
        original.extensionValue[0] = 99;
        if (copy.extensionValue[0] != 99) {
            throw new RuntimeException("extensionValue not shared as expected (shallow copy)");
        }

        System.out.println("Test passed.");
    }
}
