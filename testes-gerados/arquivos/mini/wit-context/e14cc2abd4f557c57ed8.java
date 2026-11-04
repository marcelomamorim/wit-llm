/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.getExtendedKeyUsage() method
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

import java.util.List;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetExtendedKeyUsageReturnsCorrectList();
    }

    private static void testGetExtendedKeyUsageReturnsCorrectList() throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<>();
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 1})); // id-kp-serverAuth
        usages.add(new ObjectIdentifier(new int[] {1, 3, 6, 1, 5, 5, 7, 3, 2})); // id-kp-clientAuth

        ExtendedKeyUsageExtension extKeyUsage = new ExtendedKeyUsageExtension(usages);

        List<String> ekuList = extKeyUsage.getExtendedKeyUsage();

        if (ekuList == null) {
            throw new RuntimeException("getExtendedKeyUsage() returned null");
        }

        if (ekuList.size() != usages.size()) {
            throw new RuntimeException("Expected list size " + usages.size() + ", but got " + ekuList.size());
        }

        for (int i = 0; i < usages.size(); i++) {
            String expected = usages.get(i).toString();
            String actual = ekuList.get(i);
            if (!expected.equals(actual)) {
                throw new RuntimeException("Expected OID string '" + expected + "' but got '" + actual + "' at index " + i);
            }
        }
    }
}
