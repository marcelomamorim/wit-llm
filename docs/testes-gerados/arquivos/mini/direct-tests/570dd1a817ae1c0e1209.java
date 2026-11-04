/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.get(String) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PKIXExtensions;
import java.io.IOException;
import java.util.*;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetWithValidNameReturnsMaps();
        testGetWithInvalidNameThrowsIOException();
    }

    private static void testGetWithValidNameReturnsMaps() throws IOException {
        // Prepare a list with one dummy CertificatePolicyMap
        List<CertificatePolicyMap> list = new ArrayList<>();
        // Use empty dummy CertificatePolicyMap since no constructor details provided
        CertificatePolicyMap dummyMap = new CertificatePolicyMap();
        list.add(dummyMap);

        PolicyMappingsExtension ext = new PolicyMappingsExtension(list);

        List<CertificatePolicyMap> result = ext.get("map");

        if (result == null) {
            throw new RuntimeException("Returned list is null");
        }
        if (result.size() != 1) {
            throw new RuntimeException("Returned list size expected 1 but was " + result.size());
        }
        if (result.get(0) != dummyMap) {
            throw new RuntimeException("Returned list element is not the expected map instance");
        }
    }

    private static void testGetWithInvalidNameThrowsIOException() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
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
