/* @test    @summary Test for DistributionPointName constructor with null GeneralNames    @run main DistributionPointNameWitupTest */

import sun.security.x509.DistributionPointName;
import sun.security.x509.GeneralNames;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullGeneralNames();
    }

    public static void testConstructorWithNullGeneralNames() {
        try {
            new DistributionPointName((GeneralNames) null);
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            if ("fullName must not be null".equals(e.getMessage())) {
                System.out.println("Test passed: IllegalArgumentException thrown as expected.");
            } else {
                System.err.println("Test failed: Unexpected exception message: " + e.getMessage());
                System.exit(1);
            }
        } catch (Throwable t) {
            System.err.println("Test failed: Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
