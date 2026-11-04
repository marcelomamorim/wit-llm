/*
 * @test
 * @summary Test getExtendedKeyUsage method of ExtendedKeyUsageExtension
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import java.util.List;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testGetExtendedKeyUsageEmpty();
        testGetExtendedKeyUsageWithValues();
    }

    static void testGetExtendedKeyUsageEmpty() {
        try {
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            List<String> usages = ext.getExtendedKeyUsage();
            assert usages != null : "Usages list should not be null";
            assert usages.isEmpty() : "Usages list should be empty";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetExtendedKeyUsageEmpty", e);
        }
    }

    static void testGetExtendedKeyUsageWithValues() {
        try {
            // Create a Vector of ObjectIdentifier
            java.util.Vector<sun.security.util.ObjectIdentifier> v = new java.util.Vector<>();
            v.add(new sun.security.util.ObjectIdentifier("1.3.6.1.5.5.7.3.1"));
            v.add(new sun.security.util.ObjectIdentifier("2.5.29.37.0"));
            // Instantiate with keyUsages
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(false, v);
            List<String> usages = ext.getExtendedKeyUsage();
            assert usages != null : "Usages list should not be null";
            assert usages.size() == 2 : "Usages list should contain 2 elements";
            assert usages.get(0).equals("1.3.6.1.5.5.7.3.1") : "First usage mismatch";
            assert usages.get(1).equals("2.5.29.37.0") : "Second usage mismatch";
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testGetExtendedKeyUsageWithValues", e);
        }
    }
}
