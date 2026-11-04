/*
 * @test
 * @summary Verifies that the DistributionPointName(GeneralNames) constructor rejects a null fullName
 * @modules java.base/sun.security.x509
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        try {
            new DistributionPointName((GeneralNames) null);
            throw new RuntimeException("Expected IllegalArgumentException for null fullName");
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().contains("fullName")) {
                throw new RuntimeException("Unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
