/* @test    @summary Test delete method of CRLDistributionPointsExtension    @run main CRLDistributionPointsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CRLDistributionPointsExtension;
import java.util.Collections;
import java.util.List;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithPoints();
        testDeleteWithInvalidName();
    }

    static void testDeleteWithPoints() throws IOException {
        List<sun.security.x509.DistributionPoint> points = Collections.emptyList();
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension(points);
        ext.delete("points");
        if (!ext.get().equals(Collections.emptyList())) {
            throw new AssertionError("Expected empty list after delete points");
        }
    }

    static void testDeleteWithInvalidName() {
        List<sun.security.x509.DistributionPoint> points = Collections.emptyList();
        CRLDistributionPointsExtension ext;
        try {
            ext = new CRLDistributionPointsExtension(points);
            ext.delete("invalidName");
            throw new AssertionError("Expected IOException for invalid name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
