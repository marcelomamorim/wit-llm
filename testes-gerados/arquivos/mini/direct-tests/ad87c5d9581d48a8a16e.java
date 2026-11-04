/*
 * @test
 * @summary Test ExtendedKeyUsageExtension.delete(String) method behavior
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteUsages();
        testDeleteInvalidName();
    }

    private static void testDeleteUsages() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1})); // serverAuth OID
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        // Delete with correct name "usages" (case insensitive)
        ext.delete("usages");

        // After deletion, keyUsages should be null and encodeThis() should not throw
        // We cannot access keyUsages directly, but no exception means success
        ext.delete("USAGES"); // test case insensitivity again
    }

    private static void testDeleteInvalidName() {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1}));
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
