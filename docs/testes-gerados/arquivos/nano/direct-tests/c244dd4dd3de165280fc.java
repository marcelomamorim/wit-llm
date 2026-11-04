/* @test    @summary Test get method of ExtendedKeyUsageExtension for 'usages' attribute    @run main ExtendedKeyUsageExtensionWitupTest */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetUsages();
        testGetInvalidName();
    }

    private static void testGetUsages() throws IOException {
        Vector<ObjectIdentifier> usages = new Vector<>();
        ObjectIdentifier oid1 = new ObjectIdentifier("1.3.6.1.5.5.7.3.1");
        ObjectIdentifier oid2 = new ObjectIdentifier("1.3.6.1.5.5.7.3.2");
        usages.add(oid1);
        usages.add(oid2);
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        // Using reflection to set keyUsages for testing purposes
        java.lang.reflect.Field keyUsagesField = ExtendedKeyUsageExtension.class.getDeclaredField("keyUsages");
        keyUsagesField.setAccessible(true);
        keyUsagesField.set(ext, usages);
        Vector<ObjectIdentifier> result = ext.get("usages");
        if (result == null || result.size() != 2) {
            throw new AssertionError("Expected 2 usages, got " + (result == null ? 0 : result.size()));
        }
        if (!result.get(0).equals(oid1) || !result.get(1).equals(oid2)) {
            throw new AssertionError("Usages do not match expected OIDs");
        }
        System.out.println("testGetUsages passed.");
    }

    private static void testGetInvalidName() {
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
        try {
            ext.get("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMsg = "Attribute name [" + "invalidName" + "] not recognized by " + "CertAttrSet:ExtendedKeyUsageExtension.";
            if (!e.getMessage().contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testGetInvalidName passed.");
        }
    }
}
