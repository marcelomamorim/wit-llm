/*
 * @test
 * @summary Verify PolicyMappingsExtension.get(String) returns the maps for "map" (case-insensitive) and throws IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.IOException;
import java.util.List;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();

        List<CertificatePolicyMap> maps = ext.get(PolicyMappingsExtension.MAP);
        check(maps != null, "get(\"map\") returned null");
        check(maps.isEmpty(), "default extension should expose an empty map list");

        List<CertificatePolicyMap> upper = ext.get("MAP");
        check(upper != null && upper.isEmpty(), "get(\"MAP\") should be case-insensitive");

        List<CertificatePolicyMap> mixed = ext.get("Map");
        check(mixed != null && mixed.isEmpty(), "get(\"Map\") should be case-insensitive");

        expectIOException(ext, "policy");
        expectIOException(ext, "maps");
        expectIOException(ext, "");
    }

    private static void expectIOException(PolicyMappingsExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name: \"" + name + "\"");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
