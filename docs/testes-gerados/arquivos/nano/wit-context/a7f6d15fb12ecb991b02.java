/* @test    @summary Test for CRLDistributionPointsExtension.get(String) method handling recognized and unrecognized attribute names    @run main CRLDistributionPointsExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.CRLDistributionPointsExtension;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() throws IOException {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(points);
        List<DistributionPoint> result = ext.get("points");
        if (result != null && result == points) {
            System.out.println("PASS: get with valid name 'points'");
        } else {
            System.out.println("FAIL: get with valid name 'points'");
        }
    }

    private static void testGetWithInvalidName() {
        List<DistributionPoint> points = new ArrayList<>();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(points);
        try {
            ext.get("invalidName");
            System.out.println("FAIL: get with invalid name did not throw");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not recognized")) {
                System.out.println("PASS: get with invalid name threw IOException as expected");
            } else {
                System.out.println("FAIL: get with invalid name threw IOException but message was unexpected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: get with invalid name threw unexpected exception: " + e);
        }
    }
}
