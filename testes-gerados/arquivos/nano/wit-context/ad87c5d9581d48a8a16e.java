/*
 * @test
 * @summary Test delete method of ExtendedKeyUsageExtension for expected exception behavior
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.ExtendedKeyUsageExtension;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteWithUsages();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithUsages() {
        try {
            Vector<ObjectIdentifier> usages = new Vector<>();
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
            ext.delete("usages");
            System.out.println("testDeleteWithUsages passed");
        } catch (Exception e) {
            System.err.println("testDeleteWithUsages failed: " + e);
        }
    }

    private static void testDeleteWithInvalidName() {
        try {
            Vector<ObjectIdentifier> usages = new Vector<>();
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);
            ext.delete("invalidName");
            System.err.println("testDeleteWithInvalidName failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testDeleteWithInvalidName passed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("testDeleteWithInvalidName failed: " + e);
        }
    }
}
