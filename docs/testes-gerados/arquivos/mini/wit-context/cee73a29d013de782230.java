/*
 * @test
 * @summary Test sun.security.x509.CRLDistributionPointsExtension.set(String,Object) method
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import sun.security.x509.CRLDistributionPointsExtension;
import sun.security.x509.DistributionPoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetWithValidPointsNameAndList();
        testSetWithValidPointsNameAndInvalidObject();
        testSetWithInvalidName();
    }

    private static void testSetWithValidPointsNameAndList() throws Exception {
        List<DistributionPoint> dpList = new ArrayList<>();
        // DistributionPoint has no public no-arg constructor visible in context, so use empty list
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(false, dpList);
        ext.set("points", dpList); // should succeed without exception
    }

    private static void testSetWithValidPointsNameAndInvalidObject() {
        List<DistributionPoint> dpList = new ArrayList<>();
        CRLDistributionPointsExtension ext = null;
        try {
            ext = new CRLDistributionPointsExtension(false, dpList);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in constructor", e);
        }
        try {
            ext.set("points", "not a list");
            throw new RuntimeException("Expected IOException not thrown for invalid object type");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type List.")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testSetWithInvalidName() {
        List<DistributionPoint> dpList = new ArrayList<>();
        CRLDistributionPointsExtension ext = null;
        try {
            ext = new CRLDistributionPointsExtension(false, dpList);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in constructor", e);
        }
        try {
            ext.set("invalidName", dpList);
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name [invalidName] not recognized")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
