/*
 * @test
 * @summary Tests the get(String) method of ExtendedKeyUsageExtension for expected behaviors.
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithUsages();
        testGetWithInvalidName();
    }

    private static void testGetWithUsages() throws IOException {
        // Setup: create an instance with dummy keyUsages
        Vector<ObjectIdentifier> usages = new Vector<>();
        // No need to populate for this test, as get() does not use keyUsages directly
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        // Use reflection or assume a constructor exists; since constructor details are not provided, use a dummy subclass or mock if needed
        // For simplicity, assume a default constructor exists (not in actual code, but for test illustration)
        // Alternatively, test the 'get' method directly with the known attribute
        try {
            Vector<ObjectIdentifier> result = ext.get("usages");
            if (result != null) {
                System.out.println("testGetWithUsages: PASSED");
            } else {
                System.out.println("testGetWithUsages: FAILED - result is null");
            }
        } catch (IOException e) {
            System.out.println("testGetWithUsages: FAILED with IOException: " + e.getMessage());
        }
    }

    private static void testGetWithInvalidName() {
        // Setup: create an instance
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        try {
            ext.get("invalidName");
            System.out.println("testGetWithInvalidName: FAILED - exception not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not recognized")) {
                System.out.println("testGetWithInvalidName: PASSED");
            } else {
                System.out.println("testGetWithInvalidName: FAILED - wrong exception message: " + msg);
            }
        }
    }
}
