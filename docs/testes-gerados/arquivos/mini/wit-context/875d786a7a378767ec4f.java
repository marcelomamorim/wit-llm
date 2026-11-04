/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.set(String,Object) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.x509.CertificatePolicyMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidMap();
        testSetWithInvalidName();
        testSetWithInvalidObjectType();
    }

    private static void testSetWithValidMap() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        List<CertificatePolicyMap> list = new ArrayList<>();
        // We do not add any CertificatePolicyMap to keep test simple and compilable
        ext.set("map", list);
        // If no exception, test passes
    }

    private static void testSetWithInvalidName() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.set("invalidName", new ArrayList<CertificatePolicyMap>());
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidObjectType() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.set("map", "not a list");
            throw new RuntimeException("Expected IOException for invalid attribute value type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
