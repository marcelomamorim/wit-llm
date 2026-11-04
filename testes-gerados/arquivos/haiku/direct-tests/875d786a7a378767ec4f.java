/*
 * @test
 * @summary Checks PolicyMappingsExtension.set(String, Object) name matching and argument type validation
 * @modules java.base/sun.security.x509
 * @run main PolicyMappingsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.PolicyMappingsExtension;

public class PolicyMappingsExtensionWitupTest {

    interface Action {
        void run() throws Exception;
    }

    static void expectIOException(Action action, String what) throws Exception {
        try {
            action.run();
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException: " + what);
    }

    public static void main(String[] args) throws Exception {
        PolicyMappingsExtension ext = new PolicyMappingsExtension();

        List<Object> emptyList = new ArrayList<Object>();

        // Name matching is case-insensitive and a List value is accepted.
        ext.set("map", emptyList);
        ext.set("MAP", emptyList);
        ext.set("Map", new ArrayList<Object>());

        // A non-List value for the MAP attribute must be rejected.
        expectIOException(() -> ext.set("map", "not a list"), "non-List value for map");
        expectIOException(() -> ext.set("MAP", Integer.valueOf(1)), "Integer value for MAP");
        expectIOException(() -> ext.set("map", null), "null value for map");

        // An unrecognized attribute name must be rejected.
        expectIOException(() -> ext.set("unknown", emptyList), "unknown attribute name");
        expectIOException(() -> ext.set("", emptyList), "empty attribute name");

        // A rejected call must not prevent later valid calls.
        ext.set("map", emptyList);
    }
}
