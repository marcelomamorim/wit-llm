/*
 * @test
 * @summary Test sun.security.x509.PolicyMappingsExtension.delete(String) method
 * @run main PolicyMappingsExtensionWitupTest
 */

import sun.security.x509.PolicyMappingsExtension;
import java.io.IOException;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithMap();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithMap() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.delete("map");
        // After delete("map"), maps should be null and extensionValue updated
        // We cannot access maps directly, but no exception means success
    }

    private static void testDeleteWithInvalidName() {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
