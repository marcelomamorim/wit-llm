/*
 * @test
 * @summary Test sun.security.x509.Extension copy constructor
 * @run main ExtensionWitupTest
 */

import sun.security.x509.Extension;
import sun.security.util.ObjectIdentifier;

public class ExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Create an original Extension instance with known values
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 3, 4});
        boolean critical = true;
        byte[] value = new byte[] {1, 2, 3, 4, 5};
        Extension original = new Extension(oid, critical, value);

        // Use copy constructor
        Extension copy = new Extension(original);

        // Check that fields are copied correctly
        if (!copy.getId().equals(original.getId())) {
            throw new RuntimeException("extensionId not copied correctly");
        }
        if (copy.isCritical() != original.isCritical()) {
            throw new RuntimeException("critical flag not copied correctly");
        }
        byte[] copyValue = copy.getValue();
        byte[] originalValue = original.getValue();
        if (copyValue == null || originalValue == null) {
            throw new RuntimeException("extensionValue is null");
        }
        if (copyValue.length != originalValue.length) {
            throw new RuntimeException("extensionValue length mismatch");
        }
        for (int i = 0; i < copyValue.length; i++) {
            if (copyValue[i] != originalValue[i]) {
                throw new RuntimeException("extensionValue content mismatch at index " + i);
            }
        }

        // Check that the copy's extensionValue array is the same reference (shallow copy)
        if (copyValue != originalValue) {
            throw new RuntimeException("extensionValue array reference not copied (expected shallow copy)");
        }

        System.out.println("Test passed.");
    }
}
