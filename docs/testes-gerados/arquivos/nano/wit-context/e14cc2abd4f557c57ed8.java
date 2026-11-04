/* @test    @summary Test getExtendedKeyUsage() method for normal and exceptional behavior    @run main ExtendedKeyUsageExtensionWitupTest */

import sun.security.x509.ExtendedKeyUsageExtension;
import java.util.List;
import java.util.ArrayList;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testGetExtendedKeyUsageNormal();
        testGetExtendedKeyUsageEmpty();
    }

    private static void testGetExtendedKeyUsageNormal() {
        try {
            // Setup: create an instance with a non-empty keyUsages list
            List<ObjectIdentifier> oids = new ArrayList<>();
            oids.add(new ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(
                false, oids);
            List<String> result = ext.getExtendedKeyUsage();
            if (result == null || result.isEmpty()) {
                throw new AssertionError("Expected non-empty list");
            }
            if (!result.get(0).equals("1.3.6.1.5.5.7.3.1")) {
                throw new AssertionError("Unexpected value: " + result.get(0));
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testGetExtendedKeyUsageEmpty() {
        try {
            // Setup: create an instance with an empty keyUsages list
            List<ObjectIdentifier> oids = new ArrayList<>();
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(
                false, oids);
            List<String> result = ext.getExtendedKeyUsage();
            if (result == null) {
                throw new AssertionError("Result should not be null");
            }
            if (!result.isEmpty()) {
                throw new AssertionError("Expected empty list, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
