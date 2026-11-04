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
    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(points);
        List<DistributionPoint> result = ext.get("points");
        if (result == null) {
            throw new RuntimeException("get(\"points\") returned null, expected empty list");
        }
        if (result != points) {
            throw new RuntimeException("get(\"points\") did not return the original distributionPoints list");
        }
    }

    private static void testGetWithInvalidName() {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(points);
        try {
            ext.get("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("invalidName") || !msg.contains("CertAttrSet")) {
                throw new RuntimeException("IOException message does not contain expected text: " + msg);
            }
        }
    }
}
