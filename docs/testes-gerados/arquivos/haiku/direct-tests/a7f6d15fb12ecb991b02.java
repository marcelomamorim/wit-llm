/*
 * @test
 * @summary Verify CRLDistributionPointsExtension.get(String) returns the distribution points for the "points" name (case-insensitive) and throws IOException otherwise
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
        testGetReturnsSameListForPoints();
        testGetIsCaseInsensitive();
        testGetUnknownNameThrowsIOException();
        testGetEmptyNameThrowsIOException();
        System.out.println("PASSED");
    }

    static void testGetReturnsSameListForPoints() throws Exception {
        List<DistributionPoint> input = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(input);
        List<DistributionPoint> result = ext.get("points");
        if (result == null) {
            throw new RuntimeException("get(\"points\") returned null");
        }
        if (!result.isEmpty()) {
            throw new RuntimeException("Expected empty list, got size " + result.size());
        }
    }

    static void testGetIsCaseInsensitive() throws Exception {
        List<DistributionPoint> input = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(input);
        List<DistributionPoint> lower = ext.get("points");
        List<DistributionPoint> upper = ext.get("POINTS");
        List<DistributionPoint> mixed = ext.get("Points");
        if (upper == null || mixed == null) {
            throw new RuntimeException("Case-variant lookup returned null");
        }
        if (upper.size() != lower.size() || mixed.size() != lower.size()) {
            throw new RuntimeException("Case-variant lookups returned different results");
        }
    }

    static void testGetUnknownNameThrowsIOException() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        try {
            ext.get("unknown");
            throw new RuntimeException("Expected IOException for unknown name");
        } catch (IOException expected) {
            // expected
        }
    }

    static void testGetEmptyNameThrowsIOException() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(new ArrayList<DistributionPoint>());
        try {
            ext.get("");
            throw new RuntimeException("Expected IOException for empty name");
        } catch (IOException expected) {
            // expected
        }
    }
}
