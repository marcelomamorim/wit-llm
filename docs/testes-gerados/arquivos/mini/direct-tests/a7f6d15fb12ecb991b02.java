/*
 * @test
 * @summary Test sun.security.x509.CRLDistributionPointsExtension.get(String) method
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {
    private static final String POINTS = "points";

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<DistributionPoint> points = new ArrayList<>();
        DistributionPoint dp = new DistributionPoint();
        points.add(dp);

        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(true, points);

        List<DistributionPoint> result = ext.get(POINTS);

        if (result == null) {
            throw new RuntimeException("Returned list is null");
        }
        if (result.size() != 1) {
            throw new RuntimeException("Returned list size expected 1 but was " + result.size());
        }
        if (result.get(0) != dp) {
            throw new RuntimeException("Returned list element is not the expected DistributionPoint instance");
        }
    }

    private static void testGetWithInvalidName() {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(true, points);

        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException was not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalidName") || !msg.contains("not recognized")) {
                throw new RuntimeException("IOException message does not contain expected text: " + msg);
            }
        }
    }
}
