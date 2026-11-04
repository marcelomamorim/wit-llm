/*
 * @test
 * @summary Verify PolicyMappingsExtension.set accepts a List for the map attribute (case-insensitive) and rejects invalid names and value types with IOException
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.set("map", new ArrayList<Object>());
        ext.set("MAP", Collections.emptyList());
        ext.set("Map", new ArrayList<Object>());

        expectIOException(ext, "map", "not a list");
        expectIOException(ext, "map", Integer.valueOf(1));
        expectIOException(ext, "unknown", new ArrayList<Object>());
        expectIOException(ext, "", new ArrayList<Object>());
    }

    static void expectIOException(PolicyMappingsExtension ext, String name, Object obj) {
        try {
            ext.set(name, obj);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name + " value=" + obj);
    }
}
