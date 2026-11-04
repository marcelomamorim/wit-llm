/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.delete(String) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import java.io.IOException;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        // Initially maps is empty list
        ext.delete("map");
        // After delete("map"), maps should be null and extensionValue updated
        // We cannot access maps directly, but no exception means success
        // Also test that calling delete again with "map" does not throw
        ext.delete("map");
    }

    private static void testDeleteWithInvalidName() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
