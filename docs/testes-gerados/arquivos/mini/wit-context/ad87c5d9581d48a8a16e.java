/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.delete(String) method
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1})); // serverAuth OID
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        // Delete with name "usages" should clear keyUsages and not throw
        ext.delete("usages");

        // After deletion, keyUsages should be null, but no direct getter,
        // so we rely on no exception and ability to encode without error
        ext.encodeThis();
    }

    private static void testDeleteWithInvalidName() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1}));
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
