/*
 * @test
 * @summary PolicyMappingsExtension.delete removes the map value case-insensitively and rejects unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteMapUpperCase();
        testDeleteMapLowerCase();
        testDeleteMapTwiceIsAccepted();
        testDeleteUnknownNameThrows();
        testDeleteUnknownNameDoesNotAcceptMap();
    }

    static void testDeleteMapUpperCase() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.delete("MAP");
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Extension value should be null after delete(MAP)");
        }
    }

    static void testDeleteMapLowerCase() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.delete("map");
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Extension value should be null after delete(map)");
        }
    }

    static void testDeleteMapTwiceIsAccepted() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.delete("map");
        ext.delete("Map");
        if (ext.getExtensionValue() != null) {
            throw new RuntimeException("Extension value should remain null after repeated delete");
        }
    }

    static void testDeleteUnknownNameThrows() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for unknown attribute name");
        }
    }

    static void testDeleteUnknownNameDoesNotAcceptMap() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.delete("maps");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IOException expected for name 'maps'");
        }
    }
}
