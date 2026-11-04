/*
 * @test
 * @summary Verify CRLDistributionPointsExtension.delete(String) accepts "points" case-insensitively and rejects other attribute names with IOException
 * @modules java.base/sun.security.x509
 * @run main CRLDistributionPointsExtensionWitupTest
 */

import java.io.IOException;
import java.util.ArrayList;
import sun.security.x509.CRLDistributionPointsExtension;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteUnknownNameThrowsIOException();
        testDeleteLowerCasePointsSucceeds();
        testDeleteUpperCasePointsSucceeds();
        testDeleteMixedCasePointsSucceeds();
        testDeleteEmptyNameThrowsIOException();
    }

    static void testDeleteUnknownNameThrowsIOException() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, new ArrayList<>());
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for unknown attribute name");
        }
    }

    static void testDeleteLowerCasePointsSucceeds() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, new ArrayList<>());
        ext.delete("points");
    }

    static void testDeleteUpperCasePointsSucceeds() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, new ArrayList<>());
        ext.delete("POINTS");
    }

    static void testDeleteMixedCasePointsSucceeds() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, new ArrayList<>());
        ext.delete("Points");
    }

    static void testDeleteEmptyNameThrowsIOException() throws Exception {
        CRLDistributionPointsExtension ext =
                new CRLDistributionPointsExtension(false, new ArrayList<>());
        boolean thrown = false;
        try {
            ext.delete("");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IOException for empty attribute name");
        }
    }
}
