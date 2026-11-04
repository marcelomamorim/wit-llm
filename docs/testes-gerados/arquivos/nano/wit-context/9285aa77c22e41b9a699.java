/* @test    @summary Test for DistributionPointName constructor with null RDN to verify exception throwing    @run main DistributionPointNameWitupTest */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullRDN();
    }

    private static void testConstructorWithNullRDN() {
        try {
            new DistributionPointName((RDN) null);
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }
}
