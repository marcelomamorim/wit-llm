/*
 * @test
 * @summary Checks PolicyMappingsExtension.delete accepts only the "map" name (case-insensitive) and throws IOException otherwise
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CertificatePolicyMap;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        deleteAcceptedForMapName("map");
        deleteAcceptedForMapName("MAP");
        deleteAcceptedForMapName("Map");
        deleteOnPopulatedExtensionAccepted();
        deleteRejectedForName("policy");
        deleteRejectedForName("");
        deleteRejectedForName("maps");
        deleteRejectedForName(" map");
        failedDeleteDoesNotPreventValidDelete();
    }

    static void deleteAcceptedForMapName(String name) throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        ext.delete(name);
    }

    static void deleteOnPopulatedExtensionAccepted() throws Exception {
        List<CertificatePolicyMap> maps = new ArrayList<CertificatePolicyMap>();
        PolicyMappingsExtension ext = new PolicyMappingsExtension(maps);
        ext.delete("map");
    }

    static void deleteRejectedForName(String name) throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.delete(name);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for name: \"" + name + "\"");
        }
    }

    static void failedDeleteDoesNotPreventValidDelete() throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();
        boolean thrown = false;
        try {
            ext.delete("invalid");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for name: \"invalid\"");
        }
        ext.delete("map");
    }
}
