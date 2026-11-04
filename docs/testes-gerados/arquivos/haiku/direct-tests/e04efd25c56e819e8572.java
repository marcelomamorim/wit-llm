/*
 * @test
 * @summary Verify that the GeneralNames constructor of DistributionPointName rejects a null fullName with IllegalArgumentException and the expected message
 * @modules java.base/sun.security.x509
 * @run main DistributionPointNameWitupTest
 */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        try {
            new DistributionPointName((GeneralNames) null);
            throw new AssertionError("Expected IllegalArgumentException for null fullName");
        } catch (IllegalArgumentException e) {
            String expected = "fullName must not be null";
            if (!expected.equals(e.getMessage())) {
                throw new AssertionError("Unexpected message: " + e.getMessage());
            }
        }
    }
}
