/*
 * @test
 * @summary Test sun.security.x509.ExtendedKeyUsageExtension.get(String) method
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.ObjectIdentifier;

import java.io.IOException;
import java.util.Vector;

public class ExtendedKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetUsagesReturnsSameVector();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetUsagesReturnsSameVector() throws IOException {
        Vector<ObjectIdentifier> usages = new Vector<>();
        ObjectIdentifier oid1 = new ObjectIdentifier(new int[] {1, 2, 3});
        ObjectIdentifier oid2 = new ObjectIdentifier(new int[] {1, 2, 840, 113549});
        usages.add(oid1);
        usages.add(oid2);

        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        Vector<ObjectIdentifier> returned = ext.get("usages");

        if (returned == null) {
            throw new RuntimeException("Returned vector is null");
        }

        if (returned.size() != usages.size()) {
            throw new RuntimeException("Returned vector size differs from original");
        }

        for (int i = 0; i < usages.size(); i++) {
            if (!returned.get(i).equals(usages.get(i))) {
                throw new RuntimeException("Returned vector element differs from original at index " + i);
            }
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        Vector<ObjectIdentifier> usages = new Vector<>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(usages);

        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
