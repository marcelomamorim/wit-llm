/* @test    @summary Test delete method of CRLDistributionPointsExtension for expected exception behavior    @run main CRLDistributionPointsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.CRLDistributionPointsExtension;

public class CRLDistributionPointsExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteWithUnrecognizedName();
    }

    private static void testDeleteWithUnrecognizedName() {
        CRLDistributionPointsExtension ext = new CRLDistributionPointsExtension();
        String invalidName = "invalidName";
        try {
            ext.delete(invalidName);
            // If no exception thrown, test fails
            System.err.println("Expected IOException not thrown for name: " + invalidName);
            System.exit(1);
        } catch (IOException e) {
            String message = e.getMessage();
            if (message != null && message.contains(invalidName)) {
                System.out.println("Passed: IOException thrown as expected for name: " + invalidName);
            } else {
                System.err.println("Failed: IOException message does not contain expected text. Message: " + message);
                System.exit(1);
            }
        }
    }
}
