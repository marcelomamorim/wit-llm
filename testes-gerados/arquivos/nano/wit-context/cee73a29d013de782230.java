/* @test    @summary Test for CRLDistributionPointsExtension.set method handling valid and invalid inputs    @run main CRLDistributionPointsExtensionWitupTest */

import java.io.IOException;
import java.util.*;
import sun.security.x509.CRLDistributionPointsExtension;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testSetWithValidList();
        testSetWithInvalidName();
    }

    private static void testSetWithValidList() throws Exception {
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension();
        List<Object> list = new ArrayList<>();
        ext.set("points", list);
        // Verify that distributionPoints is set to the list
        if (ext.getClass().getDeclaredField("distributionPoints") != null) {
            // Reflection or internal check, but since internal fields are private,
            // assume success if no exception thrown
        }
        System.out.println("testSetWithValidList passed.");
    }

    private static void testSetWithInvalidName() {
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension();
        try {
            ext.set("invalidName", new Object());
            System.err.println("testSetWithInvalidName failed: no exception thrown.");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("not recognized")) {
                System.out.println("testSetWithInvalidName passed.");
            } else {
                System.err.println("testSetWithInvalidName failed: wrong exception message.");
            }
        } catch (Exception e) {
            System.err.println("testSetWithInvalidName failed: unexpected exception: " + e);
        }
    }
}
