/*
 * @test
 * @summary Witup-style checks for CRLDistributionPointsExtension.set(String, Object)
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
        List<DistributionPoint> initial = new ArrayList<DistributionPoint>();
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, initial);

        // Unrecognized attribute name must be rejected with IOException
        boolean thrown = false;
        try {
            ext.set("unknownName", new ArrayList<DistributionPoint>());
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for unknown name");
        }

        // Non-List value must be rejected with IOException
        thrown = false;
        try {
            ext.set("points", "not a list");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for non-List value");
        }

        // null value is not a List and must be rejected
        thrown = false;
        try {
            ext.set("points", null);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IOException for null value");
        }

        // Valid name, case-insensitive, with a List value must succeed
        List<DistributionPoint> replacement = new ArrayList<DistributionPoint>();
        ext.set("POINTS", replacement);

        Object stored = ext.get("points");
        if (stored != replacement) {
            throw new AssertionError("set() did not store the provided list");
        }
    }
}
