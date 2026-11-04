/*
 * @test
 * @summary Test sun.security.x509.CRLDistributionPointsExtension.delete method
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import sun.security.util.ObjectIdentifier;
import java.io.IOException;
import java.util.*;

public class CRLDistributionPointsExtensionWitupTest {
    private static final String POINTS = "points";

    public static void main(String[] args) throws Exception {
        testDeleteWithValidName();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithValidName() throws Exception {
        List<DistributionPoint> points = new ArrayList<>();
        points.add(new DistributionPoint(null, null, null));
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(true, points);

        ext.delete(POINTS);

        // After delete with valid name, distributionPoints should be empty list
        Object val = ext.get(POINTS);
        if (!(val instanceof List)) {
            throw new RuntimeException("Expected a List after delete");
        }
        @SuppressWarnings("unchecked")
        List<DistributionPoint> list = (List<DistributionPoint>) val;
        if (!list.isEmpty()) {
            throw new RuntimeException("Expected empty list after delete");
        }
    }

    private static void testDeleteWithInvalidName() throws Exception {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(true, points);

        try {
            ext.delete("invalidName");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
