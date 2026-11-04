/* @test    @summary Test DistributionPointName constructor with RDN relativeName    @run main DistributionPointNameWitupTest */

import sun.security.x509.DistributionPointName;
import sun.security.x509.RDN;

public class DistributionPointNameWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidRDN();
        testConstructorWithNullRDN();
    }

    private static void testConstructorWithValidRDN() {
        RDN rdn = new RDN(); // Assuming RDN has a public no-arg constructor
        DistributionPointName dpName = new DistributionPointName(rdn);
        assert dpName != null : "DistributionPointName should not be null when constructed with valid RDN";
    }

    private static void testConstructorWithNullRDN() {
        try {
            new DistributionPointName((RDN) null);
            assert false : "Expected IllegalArgumentException when passing null RDN";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("relativeName must not be null") : "Unexpected exception message";
        }
    }
}
