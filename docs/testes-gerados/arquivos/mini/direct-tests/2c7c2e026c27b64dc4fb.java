/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.set method behavior
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidVector();
        testSetWithInvalidType();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidVector() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 2, 3}));
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        Vector<ObjectIdentifier> newUsages = new Vector<>();
        newUsages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1}));

        ext.set("usages", newUsages);

        // After set, the keyUsages field should be updated to newUsages
        // We verify by calling toString and checking the OID string presence
        String extStr = ext.toString();
        if (!extStr.contains("1.3.6.1")) {
            throw new RuntimeException("set did not update keyUsages correctly");
        }
    }

    private static void testSetWithInvalidType() {
        try {
            Vector<ObjectIdentifier> usages = new Vector<>();
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

            ext.set("usages", "not a vector");
            throw new RuntimeException("Expected IOException for invalid obj type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.equals("Attribute value should be of type Vector.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }

    private static void testSetWithUnrecognizedName() {
        try {
            Vector<ObjectIdentifier> usages = new Vector<>();
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

            ext.set("invalidName", usages);
            throw new RuntimeException("Expected IOException for unrecognized name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.startsWith("Attribute name [invalidName] not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
