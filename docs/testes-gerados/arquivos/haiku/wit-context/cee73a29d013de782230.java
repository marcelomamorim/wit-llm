/*
 * @test
 * @summary WITUP-derived checks for CRLDistributionPointsExtension.set exception paths
 * @modules java.base/sun.security.x509
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import sun.security.x509.CRLDistributionPointsExtension;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<>());

        expectIOException(ext, "points", "not-a-list");
        expectIOException(ext, "points", null);
        expectIOException(ext, "unknown", new ArrayList<Object>());
        expectIOException(ext, "POINTSX", new ArrayList<Object>());
        expectIOException(ext, "", new ArrayList<Object>());

        ext.set("points", new ArrayList<Object>());
        ext.set("POINTS", new ArrayList<Object>());
        ext.set("Points", new ArrayList<Object>());
    }

    static void expectIOException(CRLDistributionPointsExtension ext,
                                  String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name
                + " value=" + value);
    }
}
