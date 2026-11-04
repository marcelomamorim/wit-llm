/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.set method behavior
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;
import java.util.*;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidMapList();
        testSetWithInvalidObject();
        testSetWithInvalidName();
    }

    private static void testSetWithValidMapList() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        List<CertificatePolicyMap> list = new ArrayList<>();
        // Create a dummy CertificatePolicyMap instance for testing
        CertificatePolicyMap map = new CertificatePolicyMap(
            new sun.security.util.ObjectIdentifier(new int[] {1,2,3}),
            new sun.security.util.ObjectIdentifier(new int[] {4,5,6})
        );
        list.add(map);

        ext.set("map", list);

        // After set, the internal maps list should be the same as the one set
        // We cannot access maps directly, but no exception means success
        // Also, extensionValue should not be null
        if (ext.extensionValue == null) {
            throw new RuntimeException("extensionValue should not be null after set with valid list");
        }
    }

    private static void testSetWithInvalidObject() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.set("map", "not a list");
            throw new RuntimeException("Expected IOException for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("type List")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidName() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.set("invalidName", new ArrayList<CertificatePolicyMap>());
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
