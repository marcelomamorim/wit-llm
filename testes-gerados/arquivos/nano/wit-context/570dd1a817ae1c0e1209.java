/* @test    @summary Test for PolicyMappingsExtension.get(String) method to verify behavior with valid and invalid attribute names.    @run main PolicyMappingsExtensionWitupTest */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.util.*;
import java.io.IOException;
import java.util.*;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<CertificatePolicyMap> maps = Collections.emptyList();
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        List<CertificatePolicyMap> result = ext.get("PolicyMappings");
        if (result != maps) {
            throw new AssertionError("Expected to retrieve the same list of maps");
        }
        System.out.println("testGetWithValidName passed.");
    }

    private static void testGetWithInvalidName() {
        List<CertificatePolicyMap> maps = Collections.emptyList();
        try {
            PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
            ext.get("InvalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String expectedMsg = "Attribute name not recognized by " +
                        "CertAttrSet:PolicyMappingsExtension.";
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testGetWithInvalidName passed.");
        }
    }
}
