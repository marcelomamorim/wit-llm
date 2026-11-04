/*
 * @test
 * @summary Test sun.security.x509.CRLDistributionPointsExtension.set method
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidPoints();
        testSetWithInvalidType();
        testSetWithUnrecognizedName();
    }

    private static void testSetWithValidPoints() throws Exception {
        List<DistributionPoint> points = new ArrayList<>();
        // DistributionPoint has no public constructor in context, so use empty list
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(false, points);
        ext.set("points", points);
        // If no exception, test passed
    }

    private static void testSetWithInvalidType() {
        try {
            CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(false, new ArrayList<DistributionPoint>());
            ext.set("points", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!"Attribute value should be of type List.".equals(msg)) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetWithUnrecognizedName() {
        try {
            CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(false, new ArrayList<DistributionPoint>());
            ext.set("invalidName", new ArrayList<DistributionPoint>());
            throw new RuntimeException("Expected IOException not thrown for unrecognized name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.startsWith("Attribute name [invalidName] not recognized by CertAttrSet:")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
