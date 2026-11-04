/* @test    @summary Test PolicyMappingsExtension.get method for valid and invalid attribute names    @run main PolicyMappingsExtensionWitupTest */

import sun.security.x509.PolicyMappingsExtension;
import sun.security.util.CertificatePolicyMap;
import java.io.IOException;
import java.util.*;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        PolicyMappingsExtension extension = new PolicyMappingsExtension(maps);
        List<CertificatePolicyMap> result = extension.get("map");
        if (result != null && result == maps) {
            System.out.println("PASS: get with valid name returns the correct list.");
        } else {
            System.out.println("FAIL: get with valid name did not return the expected list.");
        }
    }

    private static void testGetWithInvalidName() {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        try {
            PolicyMappingsExtension extension = new PolicyMappingsExtension(maps);
            extension.get("invalidName");
            System.out.println("FAIL: get with invalid name did not throw IOException.");
        } catch (IOException e) {
            String expectedMsg = "Attribute name not recognized by " +
                "CertAttrSet:PolicyMappingsExtension.";
            if (e.getMessage().equals(expectedMsg)) {
                System.out.println("PASS: get with invalid name throws expected IOException.");
            } else {
                System.out.println("FAIL: get with invalid name throws IOException with unexpected message.");
            }
        }
    }
}
