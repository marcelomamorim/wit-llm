/*
 * @test
 * @summary Checks CRLDistributionPointsExtension.get(String) name matching and IOException for unrecognized names
 * @modules java.base/sun.security.x509
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        List<DistributionPoint> points = new ArrayList<DistributionPoint>();
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, points);

        List<DistributionPoint> got = ext.get("points");
        check(got != null, "get(\"points\") returned null");
        check(got.isEmpty(), "get(\"points\") should return the empty list");

        got = ext.get("POINTS");
        check(got != null && got.isEmpty(),
              "get(\"POINTS\") should match case-insensitively");

        got = ext.get("Points");
        check(got != null && got.isEmpty(),
              "get(\"Points\") should match case-insensitively");

        expectIOException(ext, "other");
        expectIOException(ext, "");
        expectIOException(ext, "pointss");
    }

    static void expectIOException(CRLDistributionPointsExtension ext, String name) {
        try {
            ext.get(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for name: " + name);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
