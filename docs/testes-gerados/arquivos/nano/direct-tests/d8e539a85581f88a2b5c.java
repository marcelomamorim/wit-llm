/* @test    @summary Test delete method of PolicyMappingsExtension for recognized attribute name    @run main PolicyMappingsExtensionWitupTest */

import sun.security.x509.PolicyMappingsExtension;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import sun.security.util.CertificatePolicyMap;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithValidMap();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidMap() throws IOException {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        // Using empty list for simplicity
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        ext.delete("map");
        // After deletion, maps should be null
        if (ext.maps != null) {
            throw new AssertionError("Expected maps to be null after delete");
        }
        System.out.println("testDeleteWithValidMap passed.");
    }

    private static void testDeleteWithInvalidName() {
        List<CertificatePolicyMap> maps = new ArrayList<>();
        try {
            PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
            System.out.println("testDeleteWithInvalidName passed.");
        }
    }
}
