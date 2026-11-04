/* @test    @summary Test setting distribution points in CRLDistributionPointsExtension    @run main CRLDistributionPointsExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidType();
    }

    static void testSetWithValidList() throws Exception {
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension();
        List<DistributionPoint> points = new ArrayList<>();
        DistributionPoint dp = new DistributionPoint();
        points.add(dp);
        ext.set("CRLDistributionPoints", points);
        System.out.println("testSetWithValidList passed");
    }

    static void testSetWithInvalidType() {
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension();
        try {
            ext.set("CRLDistributionPoints", "InvalidType");
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            System.out.println("testSetWithInvalidType passed");
        }
    }
}
