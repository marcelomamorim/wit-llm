/*
 * @test
 * @summary Test DistributionPointName constructor with GeneralNames argument
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;

public class DistributionPointNameWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidGeneralNames();
        testConstructorWithNullGeneralNames();
    }

    private static void testConstructorWithValidGeneralNames() {
        GeneralName gn = new GeneralName(GeneralName.RFC822_NAME, "test@example.com");
        GeneralNames gns = new GeneralNames();
        gns.add(gn);
        DistributionPointName dpn = new DistributionPointName(gns);
        if (dpn == null) {
            throw new RuntimeException("DistributionPointName constructor returned null");
        }
    }

    private static void testConstructorWithNullGeneralNames() {
        try {
            new DistributionPointName(null);
            throw new RuntimeException("Expected IllegalArgumentException for null fullName");
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("fullName must not be null")) {
                throw new RuntimeException("IllegalArgumentException message unexpected: " + msg);
            }
        }
    }
}
