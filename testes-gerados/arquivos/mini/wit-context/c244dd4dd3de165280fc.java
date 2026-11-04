/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.get(String) method
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.IOException;
import java.util.Vector;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
        System.out.println("All tests passed.");
    }

    private static void testGetWithValidName() throws IOException {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1})); // serverAuth OID
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        Vector<ObjectIdentifier> result = ext.get("usages");
        if (result == null) {
            throw new AssertionError("get(\"usages\") returned null");
        }
        if (result.size() != usages.size()) {
            throw new AssertionError("Returned vector size mismatch");
        }
        if (!result.get(0).equals(usages.get(0))) {
            throw new AssertionError("Returned vector content mismatch");
        }
    }

    private static void testGetWithInvalidName() {
        Vector<ObjectIdentifier> usages = new Vector<>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException not thrown for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
