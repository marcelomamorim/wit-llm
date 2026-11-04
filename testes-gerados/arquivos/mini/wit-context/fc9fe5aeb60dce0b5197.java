/*
 * @test
 * @summary Test sun.security.x509.CRLDistributionPointsExtension.delete(String) method
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        // Prepare a CRLDistributionPointsExtension with one dummy DistributionPoint
        DistributionPoint dp = new DistributionPoint(Collections.emptyMap(), null, null);
        List<DistributionPoint> dps = Collections.singletonList(dp);
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(dps);

        // Call delete with "points" (case insensitive) - should clear distributionPoints list
        ext.delete("points");

        // After delete, distributionPoints should be empty
        // We check by encoding and verifying no exception is thrown
        ext.encodeThis();
    }

    private static void testDeleteWithInvalidName() {
        try {
            DistributionPoint dp = new DistributionPoint(Collections.emptyMap(), null, null);
            List<DistributionPoint> dps = Collections.singletonList(dp);
            CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(dps);

            // Call delete with invalid name - should throw IOException
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
