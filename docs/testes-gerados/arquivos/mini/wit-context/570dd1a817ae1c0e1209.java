/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.get(String) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PKIXExtensions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        // We do not have public constructors or methods to create CertificatePolicyMap instances,
        // so we test with empty list to verify get returns the same list instance.
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        List<CertificatePolicyMap> result = ext.get("map");
        if (result != maps) {
            throw new RuntimeException("get(\"map\") did not return the expected list instance");
        }
    }

    private static void testGetWithInvalidName() {
        try {
            PolicyMappingsExtension ext = new PolicyMappingsExtension();
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
